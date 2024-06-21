import Vue from 'vue'
import VueRouter from 'vue-router'
 import RegistrationPage from "@/components/Registration";
import MainPage from "@/components/MainPage.vue";
 import NotFoundError from "@/components/Error";

Vue.use(VueRouter);

export default new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'default-page',
            component: RegistrationPage,
            beforeEnter: (to, from, next) => {
                (localStorage.getItem("jwt") !== null) ? next({name: 'auth-page'}) : next({name: 'app-page'});
            }
        },
        {
            path: '/auth',
            name: 'auth-page',
            component: RegistrationPage,

        },
        {
            path: '/app',
            name: 'app-page',
            component: MainPage,
            beforeEnter: (to, from, next) => {
                 if (localStorage.getItem("jwt")) next();
                 else next({
                     name: 'error-page-app',
                 });
            }
        },
        {
            path: '/*',
            name: 'error-page',
            component: NotFoundError,
            props: {
                default: true,
                errorCode: "404",
                errorMessage: "Данной страницы не существует"
            }
        },
        {
            path: '/error',
            name: 'error-page-app',
            component: NotFoundError,
            props: {
                default: true,
                errorCode: "401",
                errorMessage: "У вас нет доступа к приложению. Сначала авторизуйтесь!"
            }
        }
    ]
});