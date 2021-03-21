import VueRouter from "vue-router";
import lingo from "./components/Lingo";



export default  new VueRouter({
    routes:[
        {path: '/', component: lingo}

    ]

})