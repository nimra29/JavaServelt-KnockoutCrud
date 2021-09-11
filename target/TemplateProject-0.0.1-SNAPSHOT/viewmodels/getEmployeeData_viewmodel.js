define([ "binder" ], function(_binder) {
	var binder = _binder;
	function item(data){
		this.itemNumber=ko.observable(data.itemNumber)
		this.itemClass=ko.observable(data.itemClass)
		this.itemDescription=ko.observable(data.itemDescription)
		
	}

	function testVM() {
		var self = this;
		
		
		self.items=ko.observableArray([]);
		self.itemNumber=ko.observable()
		self.itemClass=ko.observable()
		self.itemDescription=ko.observable()
		var itemToDelete = new Object();
		
		
		self.errors = ko.observableArray();
		self.validationCompleted = ko.observable(false);
		
		self.EditUser = function(data){
			
			$(".bs-example-modal-smss").modal('show')
			self.itemNumber(data.itemNumber());
		  	self.itemClass(data.itemClass());
			self.itemDescription(data.itemDescription());
 		 }
		self.handleEdit=function(){
			console.log("hello again!");
			console.log(self.itemNumber());
			console.log(self.itemClass());
			console.log(self.itemDescription());
			
			var req = new Object();
			req.itemNumber=self.itemNumber();
			req.itemClass=self.itemClass();
			req.itemDescription=self.itemDescription();
			req = JSON.stringify(req);
			console.log(req);

			System.sendPutRequest("servletapi", req, function(response) {
				self.items.removeAll();
				for(var i=0; i<response.length; i++){
					self.items.push(new item({itemNumber:response[i].itemNumber,itemClass:response[i].itemClass,itemDescription:response[i].itemDescription}))
				}
				
			});
			$(".bs-example-modal-smss").modal('hide');
			
		}
		
		self.onLoad = function() {
			
		//	req.id= self.id();
			var req = new Object();
			System.sendGetRequest("servletapi", req, function(response) {
				console.log(response);
				for(var i=0; i<response.length; i++){
					self.items.push(new item({itemNumber:response[i].itemNumber,itemClass:response[i].itemClass,itemDescription:response[i].itemDescription}))
				}
				if (response.status == "success") {
					for (var i = 0; i < response.errors.length; i++) {
						self.errors.push(response.errors[i]);
					}

					self.validationCompleted(true);
				} else {
					var message = "Error: ";
					if (response.message)
						message += response.message;
					g_baseviewmodel.setFootNote("error", response.message);
				}
			});
		};
		
		self.AddUser = function(user){
			self.itemClass("")
			self.itemDescription("")
		  	$(".bs-example-modal-lg").modal('show')
		  
 		 }
		self.handleAdd=function(){
			console.log("hey");
			console.log(self.itemClass());
			console.log(self.itemDescription());
			console.log(System.getParameterByName("itemClass"))
			
			var req = new Object();
			req.itemClass=self.itemClass();
			req.itemDescription=self.itemDescription();
			req = JSON.stringify(req);
			console.log(req);

			System.sendPostRequest("servletapi", req, function(response) {
				console.log(response);
				if(response!==0){
					console.log("here i am");
					self.items.push(new item({itemNumber:response,itemClass:self.itemClass(),itemDescription:self.itemDescription()}))
				}
			});
			$(".bs-example-modal-lg").modal('hide');
		}
		
		self.DeleteUser=function(deleteitem){
			
			$(".bs-example-modal-del").modal('show')
			console.log(deleteitem.itemNumber());
			itemToDelete=deleteitem;
			
		}
		self.handleDelete=function(){
			var req = new Object();
			req.itemNumber=itemToDelete.itemNumber();
			req = JSON.stringify(req);
			console.log(req);

			System.sendDeleteRequest("servletapi", req, function(response) {
				console.log(response);
				if(response===true){
					console.log("here i am");
					self.items.remove(itemToDelete)
				
					
				}
			
			});
			$(".bs-example-modal-del").modal('hide');
		}
		
		self.navigateToAML=function(data){
			console.log(data.itemNumber());
			localStorage.setItem('id',data.itemNumber());
			window.open("http://localhost:8080/TemplateProject/#aml","_self")
		}
		self.navigateToStructure=function(){
			window.open("http://localhost:8080/TemplateProject/#structure","_self")
		}
		self.closeWindow = function() {
			window.close();
		}
	}

	return {
		getInstance : function() {
			return new testVM();
		}
	};
});
