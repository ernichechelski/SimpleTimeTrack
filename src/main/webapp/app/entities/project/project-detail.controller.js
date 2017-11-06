(function() {
    'use strict';

    angular
        .module('simpleTimeTrackApp')
        .controller('ProjectDetailController', ProjectDetailController);

    ProjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Project', 'User','Principal'];

    function ProjectDetailController($scope, $rootScope, $stateParams, previousState, entity, Project, User, Principal) {
        var vm = this;

        vm.project = entity;
        vm.previousState = previousState.name;
        vm.isAdmin = false;
        checkIfAdmin();

        function checkIfAdmin() {
            Principal.identity().then(function(account) {
                if ( $.inArray('ROLE_ADMIN', $(account.authorities)) > -1 ){
                    vm.isAdmin = true;
                }
            });
        }

        var unsubscribe = $rootScope.$on('simpleTimeTrackApp:projectUpdate', function(event, result) {
            vm.project = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
