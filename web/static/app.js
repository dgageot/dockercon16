"use strict";

var lab = angular.module('lab', []);

lab.controller('LabCtrl', function ($scope, $http) {
    $scope.noun1 = "";
    $scope.noun2 = "";
    $scope.adjective1 = "";
    $scope.adjective2 = "";
    $scope.verb = "";

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
