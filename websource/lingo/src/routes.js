import VueRouter from "vue-router";
import lingo from "./components/Lingo";
import Auth from "@/components/Auth";



export default  new VueRouter({
    routes:[
        {path: '/', component: Auth},
        {path: "/Lingo", component: lingo},







    ]

})