var exec = require('cordova/exec');

exports.echo = function(arg0, success, error) {
    exec(success, error, "Echo", "echo", [arg0]);
};

exports.echoNavitiaSDK = function(arg0, success, error) {
    exec(success, error, "NavitiaSDKBridge", "echo", [arg0]);
};

exports.placesGetPlaces = function(arg0, success, error) {
    exec(success, error, "NavitiaSDKBridge", "places.getPlaces", [arg0]);
};
