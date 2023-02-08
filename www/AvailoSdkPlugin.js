var exec = require('cordova/exec');

exports.initSDK = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'initSDK', [arg0]);
};
exports.loginByCredentials = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'loginByCredentials', [arg0]);
};
exports.stopScan = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'stopScan', [arg0]);
};
exports.getLastTransaction = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'getLastTransaction', [arg0]);
};
exports.startScan = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'startScan', [arg0]);
};
exports.attendByVoice = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'attendByVoice', [arg0]);
};
exports.attendByFace = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'attendByFace', [arg0]);
};
exports.getRandomTextForAttendance = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'getRandomTextForAttendance', [arg0]);
};
exports.registerImage = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'registerImage', [arg0]);
};
exports.registerVoiceFile = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'registerVoiceFile', [arg0]);
};
exports.getRandomTextForSignup = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'getRandomTextForSignup', [arg0]);
};
exports.changeUserStatus = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'changeUserStatus', [arg0]);
};
exports.getRequiredFilesCount = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'getRequiredFilesCount', [arg0]);
};
exports.addNewFaceFile = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'addNewFaceFile', [arg0]);
};
exports.addNewVoiceFile = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'addNewVoiceFile', [arg0]);
};
exports.getVoiceEnableInfo = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'getVoiceEnableInfo', [arg0]);
};
exports.validateRandomAttendance = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'validateRandomAttendance', [arg0]);
};
exports.getUserProfile = function (arg0, success, error) {
    exec(success, error, 'AvailoSdkPlugin', 'getUserProfile', [arg0]);
};
