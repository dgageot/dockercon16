"use strict";

var lab = angular.module('lab', []);

lab.controller('LabCtrl', function ($scope, $http) {

    $http.get('/words/noun').success(function(data, status, headers) {
        $scope.noun1 = {
            word: data,
            hostname: headers('X-WordsAspnet-Host')
        };
    });
    $http.get('/words/noun').success(function(data, status, headers) {
        $scope.noun2 ={
            word: data,
            hostname: headers('X-WordsAspnet-Host')
        };
    });
    $http.get('/words/adjective').success(function(data, status, headers) {
        $scope.adjective1 ={
            word: data,
            hostname: headers('X-WordsAspnet-Host')
        };
    });
    $http.get('/words/adjective').success(function(data, status, headers) {

        $scope.adjective2 = {
            word: data,
            hostname: headers('X-WordsAspnet-Host')
        };
    });
    $http.get('/words/verb').success(function(data, status, headers) {
        $scope.verb = {
            word: data,
            hostname: headers('X-WordsAspnet-Host')
        };
    });
    
    // $scope.share = function() {
    //   var sentence = $scope.noun1 + " " + $scope.adjective1 + " " + $scope.verb + " " + $scope.noun2 + " " + $scope.adjective2
    //   $http.post('http://docker.local:9000/likes',{'name':sentence})
    // };
});
