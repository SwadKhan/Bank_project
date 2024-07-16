/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */


$(document).ready(function () {
    var loadedTabs = JSON.parse(localStorage.getItem('loadedTabs')) || [];
    var i = loadedTabs.length;
    var activeTab = null; // Track the active tab

    if (i > 0) {
        loadTabsFromlocalStorage();
    } else {
        removeAllTabs();
    }

    $('.tab-links a').on('click', function (event) {
        event.preventDefault();
        var pageUrl = $(this).attr('href');
        var tabContentId = pageUrl.substring(pageUrl.lastIndexOf('/') + 1);

        var existingTab = $('#tabLinks').find('[href="#' + tabContentId + '"]');
        if (existingTab.length) {
            existingTab.tab('show'); // Show the tab if it already exists
            return;
        }

        $.ajax({
            url: pageUrl,
            method: 'GET',
            dataType: 'html',
            success: function (data) {
                pageUrl = pageUrl.substring(pageUrl.lastIndexOf('/') + 1);
                loadedTabs.push([{pageUrl, data}]);
                localStorage.setItem('loadedTabs', JSON.stringify(loadedTabs));
                addTab(pageUrl, data);
            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
            }
        });
    });

    function loadTabsFromlocalStorage() {
        if (Array.isArray(loadedTabs) && loadedTabs.length > 0) {
            for (var j = 0; j < loadedTabs.length; j++) {
                var pageUrl = loadedTabs[j][0].pageUrl;
                var data = loadedTabs[j][0].data;
                addTab(pageUrl, data);
            }
        } else {
            removeAllTabs();
        }
    }

    function addTab(tabContentId, content) {
        var name = tabContentId.split('.')[0];
        var $tabLink = $('<a>').addClass('nav-link tanD').attr({
            'href': '#' + tabContentId,
            'data-bs-toggle': 'tab',
            'role': 'tab',
            'aria-selected': true
        }).text(name);

        var $tabClose = $('<button>').addClass('btn-close btn-close-white btn-sm').attr({
            'type': 'button',
            'aria-label': 'Close'
        });

        var $tabLinkWrapper = $('<div>').addClass('col').append($tabLink);
        var $tabCloseWrapper = $('<div>').addClass('col-auto').append($tabClose);
        var $tabRow = $('<div>').addClass('row g-0').append($tabLinkWrapper, $tabCloseWrapper);
        var $tab = $('<li>').addClass('nav-item').append($tabRow);

        $('#tabLinks').append($tab);
        var $tabPane = $('<div>').addClass('tab-pane fade').attr({
            'id': tabContentId,
            'role': 'tabpanel'
        }).html(content);
        $('#tabContent').append($tabPane);

        $tabClose.on('click', function () {
            let index = -1;
            for (let m = 0; m < loadedTabs.length; m++) {
                const pageData = loadedTabs[m][0];
                if (pageData.pageUrl === tabContentId) {
                    index = m;
                    break;
                }
            }

            if (index >= 0) {
                localStorage.clear();
                loadedTabs = loadedTabs.filter((tab) => tab[0].pageUrl !== tabContentId);
                localStorage.setItem('loadedTabs', JSON.stringify(loadedTabs));

                var prevIndex = index - 1;
                if (prevIndex < 0) {
                    prevIndex = 0;
                }

                if ($('#tabLinks').children().length === 0) {
                    removeAllTabs();
                } else if ($('#tabLinks').children().length === 1 && index === 0) {
                    removeAllTabs();
                } else {
                    var prevTabLink = $('#tabLinks').find('[href="#' + loadedTabs[prevIndex][0].pageUrl + '"]');
                    $tab.remove();
                    $('#' + tabContentId).remove();
                    $tabPane.remove();
                    prevTabLink.click();
                }

                // Check if the tab being closed is the active tab
                var isActiveTab = $tabLink.parent().hasClass('active');

                if (isActiveTab) {
                    // If the tab being closed is active, switch to the previous tab
                    var prevTabLink = $('#tabLinks').find('[href="#' + loadedTabs[prevIndex][0].pageUrl + '"]');
                    prevTabLink.tab('show');
                }
            }
        });
        jQuery(function ($) {
            $tabLink.on('click', function () {
                $tabLink.tab('show'); // Show the tab when it's clicked
                activeTab = $tabLink; // Update the activeTab variable
            });
        });
//        if (!$('#tabLinks').children().length || activeTab === null) {
//            $tabLink.tab('show'); // Show the tab when it's added
//            activeTab = $tabLink; // Update the activeTab variable
//        }
    }

    function removeAllTabs() {
        $('#tabLinks').empty();
        $('#tabContent').empty();
        localStorage.clear();
        $('#tabContent').html(`<h1 class="nav-link" id="welcome">Welcome to our services.</h1>`);
    }
});
