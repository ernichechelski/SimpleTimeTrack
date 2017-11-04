(function() {
    'use strict';

    angular
        .module('simpleTimeTrackApp')
        .controller('ProjectDialogReportController', ProjectDialogReportController);

    ProjectDialogReportController.$inject = ['$timeout', '$scope', '$uibModalInstance', 'entity'];

    function ProjectDialogReportController ($timeout, $scope,  $uibModalInstance, entity) {
        var vm = this;

        vm.project = entity;
        vm.clear = clear;
        vm.save = save;
        vm.report = null;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
             $uibModalInstance.close(result);
         
        }

        function onSaveSuccess (result) {
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
