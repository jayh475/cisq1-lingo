import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import router from "@/routes";
import  Vuex from 'vuex';

//eventueel gedeeltes ervan gebruiken hele bundle maakt
//de applicatie slomer
import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'
Vue.use(VueMaterial)


//vuelidate
import { validationMixin } from 'vuelidate'
import {required, email, minLength, maxLength} from 'vuelidate/lib/validators'
Vue.use(required,email,minLength,maxLength);
Vue.use(validationMixin);



//routing
Vue.use(Vuex);
Vue.use(VueRouter);
Vue.use(VueMaterial);



Vue.prototype.$restip = ''
Vue.prototype.$restip = 'http://localhost:8070'


//authentication token
Vue.prototype.$auth = () => {
  return {
    headers: {
      Authorization: localStorage.getItem('auth')
    }
  }
}



const store = new Vuex.Store({
  state:{
    user:{},
    error:{}
  },
  mutations:{
    newuser (state,value){
      state.user = value;
    },
    seterror(state, value){
      state.error = value;
    }

  }

})

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')


