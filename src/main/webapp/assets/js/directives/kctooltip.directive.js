(function() {
    "use strict";
    
    angular.module("ngContacts").directive("kctooltip", kctooltip);

    kctooltip.$inject = ["$log"];
    
    function kctooltip($log) {
        var logger = $log.getInstance("kctooltip");
        
        var directive = {
            restrict: "A",
            compile: function(element, attrs) {
                // Do one-time configuration of element
                
                var linkFn = function(scope, element, attrs) {
                    logger.log("Calling link function");
                    
                    element.hover(
                        function() {
                            // mouse enter
                            $(this).tooltip("show");
                        },
                        function(){
                            // mouse leave
                            $(this).tooltip("hide");
                        }
                    );
                    
                    element.on("$destroy", function() {
                        logger.log("Destroying tooltip");
                        $(this).tooltip("destroy");
                    });
                }

                return linkFn;
            }
        };

        return directive;
    };
    
})();
