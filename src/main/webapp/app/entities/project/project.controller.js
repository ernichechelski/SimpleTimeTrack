(function() {
    'use strict';

    angular
        .module('simpleTimeTrackApp')
        .controller('ProjectController', ProjectController);

    ProjectController.$inject = ['Project','Principal'];

    function ProjectController(Project,Principal) {

        var vm = this;
        vm.isAdmin = false;
        vm.projects = [];

        loadAll();


        checkIfAdmin();

        function checkIfAdmin() {
            Principal.identity().then(function(account) {
                if ( $.inArray('ROLE_ADMIN', $(account.authorities)) > -1 ){
                    vm.isAdmin = true;
                }
            });
        }

        function loadAll() {
            Project.query(function(result) {
                vm.projects = result;
                vm.searchQuery = null;
            });
        }
    }
})();
