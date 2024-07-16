
$(document).ready(function () {
// window.onbeforeunload = function () {
//            alert("Windows is about to unload");
//            localStorage.clear();
//            // Close all tabs by navigating to the first index page
//            window.location.href = 'http://localhost:9090/WebApplication2/'; // Replace 'index.jsp' with your actual index page URL
//        };

//if there is already loadedTabs array in local storage then here it is fetched otherwise one is initialized.
    var loadedTabs = JSON.parse(localStorage.getItem('loadedTabs')) || [];
    var i = loadedTabs.length;
    console.log(i);
   

//Here, it checks if there are loaded tabs (i > 0). If there are, it calls the loadTabsFromlocalStorage function to load and display these tabs. Otherwise, it calls removeAllTabs to display a welcome message.
    if (i > 0) {
        loadTabsFromlocalStorage();
    } else {
        removeAllTabs();
    }


//on index.html file for clicking the tab-links class it will triggered and it will catch the clicked url and for tabcontentId which is used here as unique value to save those pages in the loadedTabs array of local storage.
    $('.tab-links a').on('click', function (event) {
        event.preventDefault();// prvent its default onclick characteristics.
        var pageUrl = $(this).attr('href');
        var tabContentId = pageUrl.substring(pageUrl.lastIndexOf('/') + 1);
        console.log(tabContentId);

        var existingTab = $('#tabLinks').find('[href="#' + tabContentId + '"]');
        if (existingTab.length) {
            existingTab.tab('show');
            console.log("existed");
            return;
        }

        $.ajax({
            url: pageUrl,
            method: 'GET',
            dataType: 'html',
            success: function (data) {
                pageUrl = pageUrl.substring(pageUrl.lastIndexOf('/') + 1);

                loadedTabs.push([{pageUrl, data}]);
                // console.log(data)
                localStorage.setItem('loadedTabs', JSON.stringify(loadedTabs));

                //addTab(tabContentId, data);
                addTab(pageUrl, data);

            },
            error: function (xhr, status, error) {
                console.error('Error:', error);
            }
        });
    });

//here all the previously loaded tabs are loaded again for showing them on screen  and all the page url are saved in local storage's loadedTabs named object and within that 
    function loadTabsFromlocalStorage() {
        if (Array.isArray(loadedTabs) && loadedTabs.length > 0) {
            for (var j = 0; j < loadedTabs.length; j++) {
                var pageUrl = loadedTabs[j][0].pageUrl;
                var data = loadedTabs[j][0].data;
                console.log("loaded" + pageUrl); //here for loading also the addTab function is called.
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
                    //'aria-selected': false,
        }).text(name);// .text(x)--> x shows the tabs displayed title. 


        var $tabClose = $('<button>').addClass('btn-close btn-close-white btn-sm').attr({
            'type': 'button',
            'aria-label': 'Close'
        }).css('color', 'red');


        var $tabLinkWrapper = $('<div>').addClass('col').append($tabLink);
        var $tabCloseWrapper = $('<div>').addClass('col-auto').append($tabClose);
        var $tabRow = $('<div>').addClass('row g-0').append($tabLinkWrapper, $tabCloseWrapper);
        var $tab = $('<li>').addClass('nav-item').append($tabRow);


        // var $tab = $('<li>').addClass('nav-item').append($tabLink, $tabClose);

        $('#tabLinks').append($tab);
        
        
        var $tabPane = $('<div>').addClass('tab-pane fade').attr({
            'id': tabContentId,
            'role': 'tabpanel'
        }).html(content);
        $('#tabContent').append($tabPane);

        // $tabLink.click();

//        Tab Close function
        $tabClose.on('click', function () {
            let index = -1;
            for (let m = 0; m < loadedTabs.length; m++) {
                const pageData = loadedTabs[m][0];
                if (pageData.pageUrl === tabContentId) {
                    index = m;
                    console.log(index);
                    break;

                }
            }


            if (index >= 0) {
                localStorage.clear();
                loadedTabs = loadedTabs.filter((tab) => tab[0].pageUrl !== tabContentId);
                localStorage.setItem('loadedTabs', JSON.stringify(loadedTabs));

                var prevIndex = index - 1;
                if (prevIndex < 0) {
                    prevIndex = 0; // Ensure the index doesn't go below 0
                }
                // Check if there are no tabs on page load and display the default form

                if ($('#tabLinks').children().length === 0) {
                    removeAllTabs();

                    console.log(2);

                } else if ($('#tabLinks').children().length === 1 && index === 0) {
                    removeAllTabs();

                    console.log(3);

                } else {
                    var prevTabLink = $('#tabLinks').find('[href="#' + loadedTabs[prevIndex][0].pageUrl + '"]');
                    // prevTabLink.tab('show');
                    $tab.remove(); // Remove the tab and content from the DOM
                    $('#' + tabContentId).remove();
//                    $(content).remove();
                    $tabPane.remove();
                    prevTabLink.click();
                    prevTabLink.tab('show');
                    // $('#tabLinks').remove('[href="#' + loadedTabs[index][0].pageUrl + '"]');
                    //$(content).remove();
                    console.log(5);

                }
            }

        });

//        jQuery(function ($) {
//           $(document).ready(function() {
    

                try {
                    $tabLink.tab('show');
                    // Your tab-related code here
                } catch (error) {
                    console.error("An error occurred:", error);
                }
//         });  
//        });
    }

    function removeAllTabs() {
        $('#tabLinks').empty();
        $('#tabContent').empty();
        localStorage.clear();
        $('#tabContent').html(`<h4 class="nav-link" id="welcome">Welcome to our services.</h4>`);
    }
});
