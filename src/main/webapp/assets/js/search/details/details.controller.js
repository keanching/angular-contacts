(function() {
    "use strict";
    
    angular.module("ngContacts").controller("detailsController", detailsController);

    detailsController.$inject = ["$log", "$scope", "growl", "$stateParams", "contactService", "CONSTANTS"];
    
    function detailsController($log, $scope, growl, $stateParams, contactService, CONSTANTS) {
        var logger = $log.getInstance("detailsController");
        logger.log("New detailsController created");
        
        var vm = this;
        vm.isViewMode = true;
        vm.isEditMode = false;
        vm.isSaving = false;
        vm.showSpinner = true;
        vm.contact = {};
        vm.originalCopy = {};
        vm.submitForm = submitForm;
        vm.setViewMode = setViewMode;
        vm.setEditMode = setEditMode;
        
        $scope.$on("$destroy", function() {
            logger.log("Destroying detailsController");
        });
        
        displayDetails();
        
        function displayDetails() {
            contactService.getContact($stateParams.contactId)
                .then(function(data) {
                    vm.contact = data.data.contact;
                    vm.originalCopy = angular.copy(data.data.contact);
                })
                .catch(function(error) {
                    logger.log("Unable to get contact details due to an unknown error");
                })
                .finally(function() {
                    vm.showSpinner = false;
                });
        }
        
        function submitForm(form) {
            logger.log("valid: " + form.$valid);
            logger.log("pending: " + (form.$pending != undefined));
            
            if (!form.$valid) {
                logger.log("Form is not valid, nothing submitted");
                growl.error("Unable to update contact details.<br>Please fix all errors before proceeding.");
                return false;
            }
            
            vm.isSaving = true;
            contactService.updateContact(vm.contact)
                .then(function(data) {
                    vm.originalCopy = angular.copy(vm.contact);
                    vm.isEditMode = false;
                    vm.isViewMode = true;
                    form.$setUntouched();
                    form.$setPristine();
                    var successMsg = vm.contact.firstName + " " + vm.contact.lastName + " (id=" + vm.contact.id + ") successfully saved";
                    logger.log(successMsg);
                    growl.success(successMsg);
                })
                .catch(function(error) {
                    logger.log("Unable to update contact due to an unknown error");
                })
                .finally(function() {
                    vm.isSaving = false;
                });
            
            return true;
        }
        
        function setViewMode(form) {
            vm.contact = angular.copy(vm.originalCopy);
            vm.isEditMode = false;
            vm.isViewMode = true;
            form.$setUntouched();
            form.$setPristine();
        }
        
        function setEditMode(form) {
            vm.isViewMode = false;
            vm.isEditMode = true;
            form.$setUntouched();
            form.$setPristine();
        }
    };
    
})();
