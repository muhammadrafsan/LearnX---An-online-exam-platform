# from turtle import home
# from unicodedata import name
from django.contrib import admin
from django.urls import path, include
from django.views.generic import TemplateView
from blog import views
from blog.views import *


          

urlpatterns = [
    
    path('admin/', admin.site.urls),
    path('', include('blog.urls')),
    path('login/', TemplateView.as_view(template_name="account/login.html"), name='login'),
    path('home/', TemplateView.as_view(template_name="dashboard/home.html"), name='home'),    
    path('accounts/', include('allauth.urls')),
   # path('cohort/', TemplateView.as_view(template_name="examcohort/cohort.html"), name='cohort'),
    path("chr", examcohort, name="cohort"),
    path('add_cohort', views.add_cohort, name='add_cohort'),
    path('inside_cohort_evaluator/<str:slug>/', viewCohortDetails, name='inside_cohort_evaluator'),
    # path('inside_cohort_evaluator/<str:slug>/', TemplateView.as_view(template_name="evaluator/inside_cohort_evaluator.html"), name='inside_cohort_evaluator'), 
    path("stud/<str:cohort_slug>/", saveStudent, name="addStudent"),
    path('delete_cohort/<int:pk>', delete_cohort, name='delete_cohort'),
    path('delete_student/<int:pk>', delete_student, name='delete_student'),
    path('delete_mcq/<int:pk>', delete_mcq, name='delete_mcq'),
    path('delete_viva/<int:pk>', delete_viva, name='delete_viva'),
    path('delete_assessment/<int:pk>', delete_assessment, name='delete_assessment'),
    path('create_assessment/<str:slug>/', create_assesment, name='create_assessment'), 
    path('view_students/<str:cohort_slug>/', views.viewStudents, name='view_students'),
    path('view_assessment/<str:coslug>/', viewAssessment, name='view_assessment'),
    path("inside-assesment-evaluator/<str:assesment_slug>/", inside_assesment_evaluator, name="InsideAssesment"), 
    path('available_cohorts/', viewStudentcohort, name='available_cohorts'),   
    path('available_assessments/<str:cohslug>/', availableAssessment, name='available_assessments'),   
    path('take_exam//<str:assSlug>/', get_mcq, name='take_exam'),  
    path('view_submissions/<str:sub_slug>/', view_submission, name='view_submissions'),  
    path('view_assessment_submissions/<str:assessCo_slug>/', view_assessment_submissions, name='view_assessment_submissions'),  
    path('view_inside_submissions/<str:assuSlug>/', view_inside_submissions, name='view_inside_submissions'),  
    path('edit_record/<int:pk>', edit_record, name='edit_record'),
    path('examRecordcsv', examRecordcsv, name='examRecordcsv'),
    path('already_taken_exam', TemplateView.as_view(template_name="student/already_taken_exam.html"), name='already_taken_exam'),  
]

             
