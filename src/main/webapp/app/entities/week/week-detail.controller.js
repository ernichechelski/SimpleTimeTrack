(function() {
    'use strict';

    angular
        .module('simpleTimeTrackApp')
        .controller('WeekDetailController', WeekDetailController);

    WeekDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Week', 'User', 'Project'];

    function WeekDetailController($scope, $rootScope, $stateParams, previousState, entity, Week, User, Project) {
        var vm = this;

        vm.week = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('simpleTimeTrackApp:weekUpdate', function(event, result) {
            vm.week = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
