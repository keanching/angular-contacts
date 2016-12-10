// s is underscore string library
(function(ngContactsUtils, _, s, undefined) {

    ngContactsUtils.isBlank = function(str) {
        if (_.isNull(str) || _.isUndefined(str) || s.trim(str) == "") {
            return true;
        }
        
        return false;
    };
    
    ngContactsUtils.enableLogging = function(turnOnLogging) {
        if (!window.savedConsole) {
            window.savedConsole = window.console;
        }
        
        if (turnOnLogging) {
            window.console = window.savedConsole;
        }
        else {
            window.console = {};
            var methods = ["log", "debug", "warn", "info"];
            
            _.each(methods, function(element, index, list) {
                window.console[element] = function() {};
            });
        }
    };
    
    // This is a hack to test services from the command line, disable this in a production environment
    ngContactsUtils.getService = function(serviceName, element) {
        element = element || '*[ng-app]';
        return angular.element(element).injector().get(serviceName);
    };
    
    ngContactsUtils.convertToDateStr = function(timeMs, dateFormat) {
        var dateFormatToUse = dateFormat || "MM/DD/YYYY";
        
        if (_.isNumber(timeMs)) {
            return moment(timeMs).format(dateFormatToUse);    
        }
        
        if (_.isString(timeMs) && !ngContactsUtils.isBlank(timeMs)) {
            return moment(parseInt(timeMs)).format(dateFormatToUse);
        }
        
        return "";
    };
    
})(window.ngContactsUtils = window.ngContactsUtils || {}, _, s);
