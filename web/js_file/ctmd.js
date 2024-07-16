
                        // Use noConflict() to avoid conflicts with other libraries using $
                        // $.noConflict();
                        //jQuery(document).ready(function ($) {

                        //  $("#tabs").tabs();
                        //});

                        (function ($) {
                            $(document).ready(function () {
                                $("#tabs").tabs();

                            });
                        })(jQuery);
                   

                   
                  

                        $(document).ready(function () {
                            var loadedTabs = JSON.parse(localStorage.getItem('loadedTabs'));
                            let index = -1;
                            for (let m = 0; m < loadedTabs.length; m++) {
                                const pageData = loadedTabs[m][0];
                                if (pageData.pageUrl === 'CustomerDetails.html') {
                                    index = m;
                                    break;
                                }
                            }
                            // var quantity = $('input[name="quantity"]').attr('quantity');

                            // Check if there is any saved form data in local storage for the specific tab
                            var formData = JSON.parse(localStorage.getItem('formData')) || {};
                            var currentTabContentId = 'CustomerDetails.html';
                            // console.log(currentTabContentId) // Variable to store the currently active tab content ID

                            // Load the saved form data into the form fields
                            $('form [name]').each(function () {
                                var fieldName = $(this).attr('name');
                                var formid = $(this).closest('form').attr('id');
                                //var formid = inputId;
                                if (!formData[currentTabContentId]) {
                                    formData[currentTabContentId] = {};
                                }
                                if (!formData[currentTabContentId][formid]) {
                                    formData[currentTabContentId][formid] = {};
                                }
                                // Set the value of the input field based on the form data
                                if (formData[currentTabContentId][formid][fieldName]) {
                                    $(this).val(formData[currentTabContentId][formid][fieldName]);
                                }
                            });

                            // Add event listeners to form fields to save data to local storage on input change
                            $('form [name]').on('input', function () {
                                //var formid = $(this).attr('id');
                                var formid = $(this).closest('form').attr('id');
                                var fieldName = $(this).attr('name');
                                var fieldValue = $(this).val();
                                //var quantity = $(this).attr('quantity');

                                // Update the form data for the current tab content ID
                                if (!formData[currentTabContentId]) {
                                    formData[currentTabContentId] = {};
                                }
                                if (!formData[currentTabContentId][formid]) {
                                    formData[currentTabContentId][formid] = {};
                                }
                                formData[currentTabContentId][formid][fieldName] = fieldValue;

                                if (fieldName === "amount") {
                                    //if (fieldName == "amount") {
                                    if (formData[currentTabContentId][formid][fieldName] > 4000) {
                                        $('#divPortion').show(); // Show the div portion
                                        localStorage.setItem('formData', JSON.stringify(formData));
                                    } else {
                                        $('#divPortion').hide(); // Hide the div portion
                                        formData[currentTabContentId][formid]["quantity"] = 0;
                                        localStorage.setItem('formData', JSON.stringify(formData));

                                    }
                                }
                                // Save the updated form data to local storage
                                else {
                                    localStorage.setItem('formData', JSON.stringify(formData));
                                }

                                // Check if the field value is greater than 4000 and show/hide the div portion accordingly


                            });




                        });


                    
                      //tab1
                      //  $(document).ready(function () {
                      
                      
                      
                            $("#submitButton").click(function () {
                                var name = $("input[name='name']").val();
                                var email = $("input[name='email']").val();
                                var address = $("input[name='address']").val();
                                //var id = "2"; // You can set the ID value here

                                $.ajax({
                                    type: "POST",
                                    url: "FormServlet", // Your servlet URL
                                    data: {
                                        name: name,
                                        email: email,
                                        address: address
                                                // id: id
                                    },
                                    success: function (response) {
                                        $("#result").text(response);
                                    },
                                    error: function (xhr, status, error) {
                                        $("#result").text("Error: " + error);
                                    }
                                });
                                $("#f1").hide();
                                $("#additionalInfo").show();
                            });

                            $("#closeButton").click(function () {
                                // Show the original form
                                $("#f1").show();
                                // Hide the additional content
                                $("#additionalInfo").hide();
                            });

                     //   });

            //            //tab2
                     //   $(document).ready(function () {
                            $("#submitButton2").click(function () {
                                console.log("Submit button 2 clicked!"); // Debug statement
                                var name = $("input[name='name2']").val();
                                var email = $("input[name='email2']").val();
                                var amount = $("input[name='amount']").val();
                                var quantity = $("input[name='quantity']").val();// You can set the ID value here
                                console.log("Name:", name); // Debug statement
                                console.log("Email:", email); // Debug statement
                                console.log("Amount:", amount); // Debug statement
                                console.log("Quantity:", quantity); // Debug statement


                                $.ajax({
                                    type: "POST",
                                    url: "CustomerFormtwo", //  servlet URL
                                    data: {
                                        name: name,
                                        email: email,
                                        amount: amount,
                                        quantity: quantity
                                                // id: id
                                    },
                                    success: function (response) {
                                        $("#result").text(response);
                                    },
                                    error: function (xhr, status, error) {
                                        $("#result").text("Error: " + error);
                                    }
                                });
                                $("#f2").hide();
                                $("#additionalInfo2").show();
                            });

                            $("#closeButton2").click(function () {
                                // Show the original form
                                $("#f2").show();
                                // Hide the additional content
                                $("#additionalInfo2").hide();
                            });
                    //    });

                   
                        //tab3
                   //     $(document).ready(function () {
                            $("#submitButton3").click(function () {
                                var name = $("input[name='name3']").val();
                                var email = $("input[name='email3']").val();
                                var address = $("input[name='address3']").val();
            //                            var cw = $("select[name='cw']").val();
                                var cw = document.getElementById('cw').value;


                                $.ajax({
                                    type: "POST",
                                    url: "FormServletTab3", // Your servlet URL
                                    data: {
                                        name: name,
                                        email: email,
                                        address: address,
                                        cw: cw
                                    },
                                    success: function (response) {
                                        $("#result").text(response);
                                    },
                                    error: function (xhr, status, error) {
                                        $("#result").text("Error: " + error);
                                    }
                                });
                            });
                    //.    });
                   
                    