var exec = require('cordova/exec');

module.exports.test = function (arg0, success, error) {
    exec(success, error, 'novosdk', 'test', [arg0]);
};

module.exports.enrollDeviceVisa = function (arg0, success, error) {
    exec(success, error, 'novosdk', 'enrollDeviceVisa', [arg0]);
};

module.exports.enrollCardVisa = function (arg0, success, error) {
    exec(success, error, 'novosdk', 'enrollCardVisa', [arg0]);
};

module.exports.getContentCard = function (arg0, success, error) {
    exec(success, error, 'novosdk', 'getContentCard', [arg0]);
};

module.exports.lifecycleManagerTokenVisa = function (arg0, success, error) {
    exec(success, error, 'novosdk', 'lifecycleManagerTokenVisa', [arg0]);
};

module.exports.selectCardVisa = function (arg0, success, error) {
    exec(success, error, 'novosdk', 'selectCardVisa', [arg0]);
};

module.exports.getTransactionHistory = function (arg0, success, error) {
    exec(success, error, 'novosdk', 'getTransactionHistory', [arg0]);
};
