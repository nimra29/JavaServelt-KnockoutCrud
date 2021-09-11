define([ "binder" ], function(_binder) {
	var binder = _binder;
	function structure(data){
		this.attachmentId=ko.observable(data.attachmentId)
		this.attachmentSize=ko.observable(data.attachmentSize)
		this.attachmentName=ko.observable(data.attachmentName)
		
	}

	function testVM() {
		var self = this;
		
		
		self.attachments=ko.observableArray([]);
		self.attachmentId=ko.observable()
		self.attachmentSize=ko.observable()
		self.attachmentName=ko.observable()
		
		var itemToDelete = new Object();
		
		
		self.errors = ko.observableArray();
		self.validationCompleted = ko.observable(false);
		
		self.EditUser = function(data){
			
			$(".bs-example-modal-smss").modal('show')
				self.attachmentId(data.attachmentId());
 		 }
		self.handleEdit=function(){
			
			
			var filist = document.getElementById("FileEdit");
			self.attachmentName(filist.files[0].name);
			self.attachmentSize(filist.files[0].size);
			var req = new Object();
			req.itemNumber=parseInt(localStorage.getItem('id'));
			req.attachmentSize=filist.files[0].size;
			req.attachmentName=filist.files[0].name;
			req.attachmentId=self.attachmentId();
			console.log(req.attachmentId)
			console.log(req.itemNumber)
			req = JSON.stringify(req);
			console.log(req);
			System.sendPutRequest("itemAttachmentServlet", req, function(response) {
				console.log(response);
				self.attachments.removeAll();
				for(var i=0; i<response.length; i++){
					self.attachments.push(new structure({attachmentId:response[i].attachmentId,attachmentSize:response[i].attachmentSize,attachmentName:response[i].attachmentName}))
				}
				
				
			});
			$(".bs-example-modal-smss").modal('hide');
			
		}
		
		self.onLoad = function() {
			var req = new Object();
			req.itemNumber=parseInt(localStorage.getItem('id'));
			req.action="get";
			req = JSON.stringify(req);
			console.log(req);

			System.sendPostRequest("itemAttachmentServlet", req, function(response) {
				console.log(response);
				for(var i=0; i<response.length; i++){
					self.attachments.push(new structure({attachmentId:response[i].attachmentId,attachmentSize:response[i].attachmentSize,attachmentName:response[i].attachmentName}))
				}
				
				
			});
		};
		
		self.AddUser = function(user){
			self.attachmentSize("")
			self.attachmentName("")
		  	$(".bs-example-modal-lg").modal('show')
		  
 		 }
		self.handleAdd=function(){
			
			var filist = document.getElementById("FileInput");
			self.attachmentName(filist.files[0].name);
			self.attachmentSize(filist.files[0].size);
			var req = new Object();
			req.itemNumber=parseInt(localStorage.getItem('id'));
			req.attachmentSize=filist.files[0].size;
			req.attachmentName=filist.files[0].name;
			req.action="post";
			req = JSON.stringify(req);
			console.log(req);
			System.sendPostRequest("itemAttachmentServlet", req, function(response) {
				
				if(response!==0){
					
					self.attachments.push(new structure({attachmentId:response,attachmentSize:self.attachmentSize(),attachmentName:self.attachmentName()}))
				}
				
			});
			
			$(".bs-example-modal-lg").modal('hide');
		}
		
		self.DeleteUser=function(deleteitem){
			
			$(".bs-example-modal-del").modal('show')
		
			itemToDelete=deleteitem;
			
		}
		self.handleDelete=function(){
			var req = new Object();
			req.attachmentId=itemToDelete.attachmentId();
			req = JSON.stringify(req);
			console.log(req);

			System.sendDeleteRequest("itemAttachmentServlet", req, function(response) {
				console.log(response);
				if(response===true){
					self.attachments.remove(itemToDelete)
				
					
				}
			
			});
			$(".bs-example-modal-del").modal('hide');
		}
		self.navigateToFile=function(){
			window.open("C:\fakepath\Screenshot from 2021-07-12 18-20-40.png","_self")


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
