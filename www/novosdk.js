var exec = require('cordova/exec');

module.exports.test = function (arg0, success, error) {
    exec(success, error, 'novosdk', 'test', [arg0]);
};

module.exports.enrollDeviceVisa = function (arg0, success, error) {
    exec(success, error, 'novosdk', 'enrollDeviceVisa', [arg0]);
};
