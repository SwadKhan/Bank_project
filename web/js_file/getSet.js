//var s = "name1=nahin|address1=badda|phone=123~name1=swad|address1=mohakahli|phone=456~name1=blaa|address1=mirpur|phone=789"

function getFieldsFromc1() {
    var s = document.forms[0].customData.value;
    console.log("s1: " + s1);
    var s1 = new Array();
    s1 = s.split('~');
    
    var s1Length = s1.length;
    var s2 = new Object();


    for (var s3 = 1; s3 < s1Length; s3++) {
        var s4 = s1[s3].split('|');
        console.log("s4: " + s4);
        var s5 = s4[0];
        console.log("s5: " + s5);
        var length = s4.length;
        var c1 = new Object();
        for (var c2 = 1; c2 < length; c2++) {
            var d1 = s4[c2].split('=');
            console.log("d1: " + d1);
            if (d1.length >= 2) {
                var key = d1[0];
                d1.shift();
                var value = d1.join('=');
                c1[key] = value;
            }
        }
        s2[s5] = c1;

    }
    return s2;
}

//getFieldsFromc1()

function setFieldsIncustomData() {
    var name = "p1";
    var s5 = "";
    var s = "";

    var s2 = new Object();
    var c1 = new Object();




    var sk1 = s2[name];

    for (var custKey in sk1) {
        c1[custKey] = sk1[custKey];
    }

    for (i = 0; i < arguments.length; i++) {
        var fieldObj = eval("document.forms[0]." + arguments[i]);
        var fieldVal = "";
        if (fieldObj.type === 'checkbox') {
            if (fieldObj.checked)
                fieldVal = "on";
            else
                fieldVal = "";

        } else if (typeof (fieldObj.type) === "string") {
            fieldVal = fieldObj.value;
        } else
            fieldVal = getRadioValue(fieldObj);

        c1[arguments[i]] = fieldVal;
    }

    s2[name] = c1;
    for (var sk2 in s2) {
        var sk1 = s2[sk2];
        s5 = sk2;
        s = s + '~' + s5 + '|';
        for (var c1Key in sk1) {
            d1Key = c1Key;
            d1Value = sk1[c1Key];
            if (d1Value !== null) {
                s = s + d1Key + '=' + d1Value + '|';
            }
        }
    }
    document.forms[0].customData.value = s;
//    console.log("print s: " + s)
   // alert(s);
   // return s;
    
}

function onclickbutton() {
//    var username = document.getElementById("username").value;
//    var location = document.getElementById("location").value;
//    var textare = document.getElementById("text_area")
    console.log("here");

    setFieldsIncustomData("username", "location", "w3review");
    s9=getFieldsFromc1();
    alert(s9);
}


function getRadioValue(obj) {
    var c = eval("document.forms[0]." + obj.id);
    var a = (typeof (c.length) === "undifed") ? false : true;
    if (!a) {
        return c.value;
    }
    var d = "";
    if (a && (typeof (c.type) === "undefiend")) {
        for (var b = 0; b < c.length; b++) {
            if (c[b].checked) {
                d = c[b].value;
                break
            }
        }
    }
    return d;
}
