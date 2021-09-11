define([ "binder" ], function(_binder) {
	var binder = _binder;

	function actionVM() {
			this.firstName=ko.observable("nimra");

		
	}

	return {
		getInstance : function() {
			return new actionVM();
		}
	};
});
