import { createRouter, createWebHistory } from 'vue-router'
import StudentManager from '../components/StudentManager.vue'
import CourseManager from '../components/CourseManager.vue'
import ScoreManager from '../components/ScoreManager.vue'
import ScoreStatistics from '../components/ScoreStatistics.vue'

const routes = [
  {
    path: '/',
    redirect: '/students'
  },
  {
    path: '/students',
    name: 'StudentManager',
    component: StudentManager,
    meta: { title: '学生管理' }
  },
  {
    path: '/courses',
    name: 'CourseManager',
    component: CourseManager,
    meta: { title: '课程管理' }
  },
  {
    path: '/scores',
    name: 'ScoreManager',
    component: ScoreManager,
    meta: { title: '成绩管理' }
  },
  {
    path: '/statistics',
    name: 'ScoreStatistics',
    component: ScoreStatistics,
    meta: { title: '成绩统计' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫：设置页面标题
router.beforeEach((to, from, next) => {
  if (to.meta.title) {
    document.title = `成绩管理系统 - ${to.meta.title}`
  }
  next()
})

export default router