"use strict";

var labApp = angular.module('labApp', []);

labApp.controller('LabCtrl', function ($scope, $http) {
    $http.get('/words/noun').success(function(data) {
        $scope.noun1 = data;
    });
    $http.get('/words/noun').success(function(data) {
        $scope.noun2 = data;
    });
    $http.get('/words/adjective').success(function(data) {
        $scope.adjective1 = data;
    });
    $http.get('/words/adjective').success(function(data) {
        $scope.adjective2 = data;
    });
    $http.get('/words/verb').success(function(data) {
        $scope.verb = data;
    });
});
