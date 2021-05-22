/**
 * 
 */


var List = {
	template:'<div><h3>msg list</h3>'+
             '<table border="1"><tr><th>num</th><th>writer</th><th>content</content><th>update</th></tr>'+
			'<tr v-for="m in list"><td>{{m.num}}</td><td>{{m.writer}}</td><td>{{m.content}}</td>'
			+'<td><button v-on:click="edit(m.num)">edit</button><button v-on:click="del(m.num)">delete</button>'
			+'</td></tr>'
			+'</table></div>',
	data(){
		return{
			list:[],
		};
	},
	created: function(){//시작시 실행 함수. 초기화 함수
		const self = this;
		axios.get('http://localhost:8888/msgs')//ajax 요청
		.then(function(res){//ajax 응답 받으면 실행. 서버의 응답 데이터는 함수 파라메터(res)로 받음
			//alert(res.status);//res 속성:status(응답상태코드.200은정상), data(서버가 보낸 응답)
			if(res.status==200){
				self.list = res.data.list;
				//alert(self.list[0].writer)
			}else{
				alert("fail");
			}
			
		});
	},
	methods:{
		edit:function(num){
			//alert('edit:'+num);
			app.$router.push({name:'Edit', params:{num:num}});
		},
		del:function(num){
			//alert('del:'+num);
			axios.delete('http://localhost:8888/msgs/'+ num)
			.then(function(res){
				if(res.data.result){
					//app.$router.push('/list');//페이지 이동
					window.location.reload();
				}else{
					alert("fail");
				}
			});
		}
	}
};

var Write = {
	template:'<div>'
			+'<h3>write form</h3>'
			+'<div><label for="writer">writer</label><input type="text" id="writer" v-model="writer"/><div/>'
			+'<div><label for="content">content</label><input type="text" id="content" v-model="content"/><div/>'
			+'<button v-on:click="save">save</button>'
			+'</div>',
	data(){
		return{
			writer:'',
			content:''
		};
	},
	methods:{
		save:function(){
			const form = new URLSearchParams();//폼 파라메터 생성
			form.append('writer', this.writer)
			form.append('content', this.content)
			axios.post('http://localhost:8888/msgs',form)
			.then(function(res){
				if(res.data.result){
					app.$router.push('/list');//페이지 이동
				}else{
					alert("fail");
				}
			});
		}
	}
};

var Edit = {
	template:'<div>'
			+'<h3>edit form</h3>'
			+'<div><label for="writer">writer</label><input type="text" id="writer" v-model="writer"/><div/>'
			+'<div><label for="content">content</label><input type="text" id="content" v-model="content"/><div/>'
			+'<button v-on:click="save">save</button>'
			+'</div>',
	data(){
		return{
			num:this.$route.params.num,
			writer:'',
			content:''
		};
	},
	created:function(){
		const self = this
		axios.get('http://localhost:8888/msgs/' + self.num)
		.then(function(res){
			if(res.data.result){					
					self.writer = res.data.m.writer;
					self.content = res.data.m.content;
				}else{
					alert("fail");
				}
		});
	},
	methods:{
		save:function(){
			const form = new URLSearchParams();//폼 파라메터 생성
			form.append('num', this.num);
			form.append('writer', this.writer)
			form.append('content', this.content)
			axios.put('http://localhost:8888/msgs/'+ this.num, form)
			.then(function(res){
				if(res.data.result){
					app.$router.push('/list');//페이지 이동
				}else{
					alert("fail");
				}
			});
		}
	}
};

var routes = [
	{
		path: '/list',
		component: List
	},
	{
		path: '/write',
		component: Write
	},
	{
		path: '/edit',
		component: Edit,
		name: 'Edit'
	}
];

var router = new VueRouter({
	routes
});

var app = new Vue({
	router
}).$mount('#app');




