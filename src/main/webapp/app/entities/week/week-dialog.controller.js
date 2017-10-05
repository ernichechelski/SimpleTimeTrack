(function() {
    'use strict';

    angular
        .module('simpleTimeTrackApp')
        .controller('WeekDialogController', WeekDialogController);

    WeekDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Week', 'User', 'Project'];

    function WeekDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Week, User, Project) {
        var vm = this;

        vm.week = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.projects = Project.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.week.id !== null) {
                Week.update(vm.week, onSaveSuccess, onSaveError);
            } else {
                Week.save(vm.week, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('simpleTimeTrackApp:weekUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
