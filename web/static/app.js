"use strict";

var lab = angular.module('lab', []);

lab.controller('LabCtrl', function ($scope, $http) {

    $http.post('/words/noun').success(function(data, status, headers) {
        $scope.noun1 = {
            word: data,
            hostname: headers('X-WordsAspnet-Host')
        };
    });
    $http.post('/words/noun').success(function(data, status, headers) {
        $scope.noun2 ={
            word: data,
            hostname: headers('X-WordsAspnet-Host')
        };
    });
    $http.post('/words/adjective').success(function(data, status, headers) {
        $scope.adjective1 ={
            word: data,
            hostname: headers('X-WordsAspnet-Host')
        };
    });
    $http.post('/words/adjective').success(function(data, status, headers) {

        $scope.adjective2 = {
            word: data,
            hostname: headers('X-WordsAspnet-Host')
        };
    });
    $http.post('/words/verb').success(function(data, status, headers) {
        $scope.verb = {
            word: data,
            hostname: headers('X-WordsAspnet-Host')
        };
    });
});
