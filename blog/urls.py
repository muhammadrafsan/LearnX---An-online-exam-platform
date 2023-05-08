from django.urls import include, path
from rest_framework import routers
from . import views

router = routers.DefaultRouter()
router.register(r'saveexam',views.saveExamApiView)
router.register(r'examcohort',views.ExamCohortApiView)
router.register(r'saveviva',views.VivaQuesApiView)
router.register(r'exammcq',views.McqQuesApiView)
router.register(r'assessment',views.AssessmentApiView)
router.register(r'student',views.StudentApiView) 
router.register(r'user',views.userApiView, basename='User')
#router.register(r'examcohort/', views.ExamCohortApiView, 'ExamCohort')
# Wire up our API using automatic URL routing.
# Additionally, we include login URLs for the browsable API.
urlpatterns = [
    path('', include(router.urls)),
    path('api-auth/', include('rest_framework.urls', namespace='rest_framework')),
]