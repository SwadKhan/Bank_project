var tabAccessed = {
    page1: false,
    page2: false,
    page3: false
};

function openTab(tabName) {
    if (tabAccessed[tabName] || tabName === 'page1') {
        console.log(tabName);
        const iframe = document.getElementById("pageFrame");
        iframe.src = '/other_html_file/customer_tabs/' + tabName + '.html';
        tabAccessed[tabName] = true;
    } else {
        alert("Please fill out the form in the previous tab before proceeding.");
    }
}
//console.log(page1);
function navigate(nextTabName) {
    var previousTabName = getPreviousTab(nextTabName);
    if (previousTabName && tabAccessed[previousTabName]) {
        var iframe = document.getElementById('pageFrame');
        if (iframe) {
            iframe.src = `/other_html_file/customer_tabs/${nextTabName}.html`;
            tabAccessed[nextTabName] = true;
        } else {
            console.error('Iframe element not found.');
        }
    } else {
        alert("Please fill out the form in the previous tab before proceeding.");
    }
}

function getPreviousTab(tabName) {
    switch (tabName) {
        case 'page2':
            return 'page1';
        case 'page3':
            return 'page2';
        default:
            return null;
    }
}

function saveFormData(formId) {
    var form = document.getElementById(formId);
    var formData = {};
    for (var i = 0; i < form.elements.length; i++) {
        var element = form.elements[i];
        if (element.name) {
            formData[element.name] = element.value;
        }
    }
    localStorage.setItem(formId, JSON.stringify(formData));
}

function loadFormData(formId) {
    var formData = localStorage.getItem(formId);
    if (formData) {
        formData = JSON.parse(formData);
        var form = document.getElementById(formId);
        for (var name in formData) {
            if (formData.hasOwnProperty(name)) {
                var input = form.querySelector('[name="' + name + '"]');
                if (input) {
                    input.value = formData[name];
                }
            }
        }
    }
}

function handleFormSubmit(formId, nextTabName) {
    saveFormData(formId);
    navigate(nextTabName);
}

loadFormData('form1');
openTab('page1');