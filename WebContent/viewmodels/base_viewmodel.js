define(
		[ "binder" ],
		function(_binder) {
			var binder = _binder;

			function BaseVM() {
				var self = this;

				ko.validation.init({
					errorElementClass : "has-error",
					errorMessageClass : "help-block",
					decorateInputElement : true
				}, true);

				self.msg = ko.observable();
				self.footerClass = ko.observable("alert alert-info");

				self.onLoad = function() {
				};

				self.showInventoryDataPage = function(init) {
					binder.loadView("getInventoryData",
							"getInventoryData_viewmodel", "action_div",
							"pageContainer", init);
				};
				
				self.showItemDataPage = function(init) {
					binder.loadView("getItemData",
							"getItemData_viewmodel", "action_div1",
							"pageContainer", init);
				};
				
				self.showItemStructureDataPage = function(init) {
					binder.loadView("getStructureData",
							"getStructureData_viewmodel", "action_div2",
							"pageContainer", init);
				};
				
				self.showAMLDataPage = function(init) {
					binder.loadView("getAMLData",
							"getAMLData_viewmodel", "action_div3",
							"pageContainer", init);
				};
				
				self.showAttachmentDataPage = function(init) {
					binder.loadView("getAttachmentData",
							"getAttachmentData_viewmodel", "action_div4",
							"pageContainer", init);
				};

				self.showLandingPage = function(init) {
					binder.loadView("criteria_grid", "criteria_grid_viewmodel",
							"criteria_grid_div", null, init);
				};

				self.showCreateCriteriaPage = function(init) {
					binder.loadView("create_criteria",
							"create_criteria_viewmodel", "create_criteria_div",
							null, init);
				};

				self.setFootNote = function(type, msg, timeout) {
					

					if (timeout)
						setTimeout(function() {
							$("#index-errorDiv").fadeOut();
						}, timeout);
				};

				self.hideMessage = function() {
					$("#index-errorDiv").slideUp("slow");
				};
			}

			return {
				getInstance : function() {
					return new BaseVM();
				}
			};
		});
