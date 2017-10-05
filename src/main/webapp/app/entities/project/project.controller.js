(function() {
    'use strict';

    angular
        .module('simpleTimeTrackApp')
        .controller('ProjectController', ProjectController);

    ProjectController.$inject = ['Project'];

    function ProjectController(Project) {

        var vm = this;

        vm.projects = [];

        loadAll();

        function loadAll() {
            Project.query(function(result) {
                vm.projects = result;
                vm.searchQuery = null;
            });
        }
    }
})();
