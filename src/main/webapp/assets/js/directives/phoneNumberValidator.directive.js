// Checks each character to make sure it is a number.
// The logic to do the validation is right here in the validator, it is not server side validation.
// This validator just checks to make each character entered is numeric.
(function() {
    "use strict";
    
    angular.module("ngContacts").directive("phoneNumberValidator", phoneNumberValidator);

    phoneNumberValidator.$inject = ["$log"];
    
    function phoneNumberValidator($log) {
        var logger = $log.getInstance("phoneNumberValidator");
        var allowedChars = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"];
        
        var directive = {
            require: "ngModel",
            link: function(scope, element, attrs, ngModel) {
                ngModel.$validators.phoneNumber = function(modelValue, viewValue) {
                    logger.log("Validating phone number");
                    var inputValue = modelValue || viewValue;
                    logger.log("inputValue: ", inputValue);
                    
                    if (inputValue == null || inputValue == undefined) {
                        return false;
                    }
                    
                    var isValid = true;
                    var splitArr = inputValue.trim().split(/[\s-().]+/).join("").split(""); // Remove '(', ')', '-', '.', and spaces
                    
                    for (var i = 0; i < splitArr.length; i++) {
                        var currChar = splitArr[i];
                        var index = allowedChars.indexOf(currChar);
                        
                        // If not found
                        if (index == -1) {
                            isValid = false;
                            break;
                        }
                    }
                    
                    return isValid;
                }; 
            }
        };

        return directive;
    };
    
})();
