from asyncio import streams
from asyncore import write
from chunk import Chunk
from array import array
from email.policy import default
import imp
from struct import pack
from email import message
import email
from .models import User
import json
from itertools import chain
from rest_framework import viewsets
from multiprocessing import context
import time
from urllib import response
from webbrowser import get
from xmlrpc.client import DateTime
from django import http
from django.http import HttpResponse, HttpResponsePermanentRedirect, HttpResponseRedirect
from django.shortcuts import get_object_or_404, render, redirect
from requests import request
from urllib3 import HTTPResponse
from .models import Assestment, ExamCohort, ExamMCQ, Student, saveExamRecord, saveViva
from blog import models 
from django.contrib.auth.decorators import login_required
from blog import models
from django.contrib import messages
from django.core.exceptions import ValidationError
from django.core.validators import validate_email
from django.utils.translation import gettext_lazy as _
from datetime import date, datetime
import csv
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from rest_framework import permissions
from .models import ExamCohort
from .serializers import AssessmentSerializers, ExamCohortSerializers, MCQSerializers, StudentSerializers, UserSerializers, VivaSerializers, saveExamSerializers


# Create your views here.
def examcohort(request):
    user = request.user
    if request.method == 'POST':
        if user is not None:
            ch = request.POST.get("exm")
            #sec = request.POST.get("sec")
            cohLength=len(ch)
        
            if(cohLength<=50):
                new_cohort = ExamCohort(
                    cohortName=ch, user=user)
                new_cohort.save()
            else:
                return HttpResponse("Cohort name is too long. Max length is 50 characters. Please Try Again")

            return redirect("add_cohort")

        else:
            return HttpResponse("Something Went Wrong! User not found!")

    return render(request, 'examcohort/cohort.html', {})


def viewCohortDetails(request, slug):
    details = get_object_or_404(ExamCohort, slug=slug)
    args = {
        "details": details,
    }
    return render(request, "evaluator/inside_cohort_evaluator.html", args)
    #details = ExamCohort.objects.get(slug=slug)
    

def saveStudent(request, cohort_slug):
    cohortObj = get_object_or_404(ExamCohort, slug=cohort_slug)
    
    user = request.user
    
    if request.method == "POST":
        
        email = request.POST.get("email")
        stud = Student(email=email,
                       cohortName=ExamCohort.objects.get(id=cohortObj.id), user=user)
        try:
            validate_email(email)
        except ValidationError as e:
            return HttpResponse("Invalid email")
    
        else:
            stud.save()
          
        return redirect(f"/inside_cohort_evaluator/{cohortObj.slug}/")
    
    studentEmail = Student.objects.filter(
        cohortName = cohortObj,
    )
    print(studentEmail)
    args = {
        "cohortObj": cohortObj,
        "studentEmail": studentEmail
    }
    return render(request, "evaluator/add_students.html", args)


def add_cohort(request):
    user = request.user
    print(user)
    cohort = models.ExamCohort.objects.filter(
        user=request.user
    )
    return render(request,'examcohort/add_cohort.html',{'cohort':cohort})


def create_assesment(request, slug):
    cohort_obj = get_object_or_404(ExamCohort, slug=slug)
    tempDate = date.today()
    tempTime = datetime.now().strftime('%H:%M:%S')
    print(tempTime)
    convdate = tempDate.strftime('%Y-%m-%d')

    if request.method == "POST":
        avDate = request.POST.get("availabledate")
        avTime = request.POST.get("availabletime")
        tempTitle = request.POST.get("assissment")
        titleLength = len(tempTitle)
        
        if(avDate>=convdate and titleLength<=50):
            if(avDate==convdate and avTime<tempTime):
                return HttpResponse("Invalid Time Input, Please try again! ")
    
            else:
                if request.method == "POST":
                    #pass
                    assesment = Assestment.objects.create(
                        cohort=ExamCohort.objects.get(id=cohort_obj.id),
                        assesment_title = request.POST.get("assissment"),
                        available_date = request.POST.get("availabledate"),
                        available_time =request.POST.get("availabletime"),
                        
                    )

                    
            return redirect(f"/inside_cohort_evaluator/{cohort_obj.slug}/")
        else:
            return HttpResponse("Invalid Input, Please try again! ")
    
    


    # if request.method == "POST":
    #     assesment_title = request.POST.get("assissment"),
    #     available_date = request.POST.get("availabledate"),
    #     available_time =request.POST.get("availabletime")
    #     assessment = Assestment(assesment_title=assesment_title,available_date=available_date, available_time=available_time, cohort=ExamCohort.objects.get(id=cohort_obj.id))
    
    #     assessment.save()
          
    #     return redirect(f"/inside_cohort_evaluator/{cohort_obj.slug}/")

    
    args = {
        "cohort_obj": cohort_obj,
    }
    return render(request,'evaluator/create_assessment.html', args)

def delete_cohort(request, pk):
    ddata = ExamCohort.objects.get(id=pk)
    if ddata is not None:
        ddata.delete()
        # print("delete")
        return redirect('add_cohort')
    else:
        return HttpResponse("Id Not Found!")
    

    
    
def delete_student(request, pk):
    
    ddel = Student.objects.get(id=pk)
    if ddel is not None:
        ddel.delete()
        return HttpResponseRedirect(request.META.get('HTTP_REFERER'))
    else:
        return HttpResponse("Not found!")



def delete_assessment(request, pk):
    delz = Assestment.objects.get(id=pk)
    if delz is not None:
        delz.delete()
        return HttpResponseRedirect(request.META.get('HTTP_REFERER'))
    else:
        return HttpResponse("Not found!")
    
def delete_mcq(request, pk):
    deliz = ExamMCQ.objects.get(id=pk)
    if deliz is not None:
        deliz.delete()
        return HttpResponseRedirect(request.META.get('HTTP_REFERER'))
    else:
        return HttpResponse("Not found!")
    
def delete_viva(request, pk):
    delviva = saveViva.objects.get(id=pk)
    if delviva is not None:
        delviva.delete()
        return HttpResponseRedirect(request.META.get('HTTP_REFERER'))
    else:
        return HttpResponse("Not found!")

def viewStudents(request, cohort_slug):
    cohortSlug = get_object_or_404(ExamCohort, slug=cohort_slug)
    studList = Student.objects.filter()
    user = request.user
    
    student = Student.objects.filter(
                cohortName=cohortSlug
        )

    args = {
        "student": student,
        "cohortSlug": cohortSlug,
    }
    return render(request,'evaluator/view_students.html',args)

def viewStudentcohort(request):
    
    user = request.user
    student_email = models.Student.objects.filter()
    
    for i in student_email:
        if(user.email == i.email):
            flag = 1
            if(flag==1):
                break
        else:
            flag = 0

    cohort_list = []
    for j in student_email:
        if(user.email == j.email):
            cohort_list.append(j.cohortName) 
            

    if(flag == 1):
        cohList = models.ExamCohort.objects.filter()
        avList = []
        for a in cohort_list:
            for b in cohList:
                if(a == b):
                    print('Hello')
                    avList.append(b)
    else:   
            avList = []
            html = "<html><body><h1>You are not enrolled in any cohort.</h1></body></html>"
            return HttpResponse(html)
        
        
    args = {
        'avList': avList
    }
    return render(request, 'student/available_cohorts.html', args)

def viewAssessment(request, coslug):
    cohSlug = get_object_or_404(ExamCohort, slug=coslug)
    
    assessment = models.Assestment.objects.filter(
        cohort = cohSlug
    )
    args = {
        "assessment": assessment,
        "cohSlug": cohSlug,
    }
  
    return render(request,'evaluator/view_assessment.html', args)


def availableAssessment(request, cohslug):
    cohSlug = get_object_or_404(ExamCohort, slug=cohslug)
    assessment = Assestment.objects.filter(
                cohort = cohSlug
            )
    
    args = {
        "assessment": assessment,
        "cohSlug": cohSlug,
     
    }
  
    return render(request,'student/available_assessments.html', args)

def inside_assesment_evaluator(request, assesment_slug):
    assesObj = get_object_or_404(Assestment, slug=assesment_slug)
    
    viewMcq = ExamMCQ.objects.filter(
        assessmentTitle = assesObj
    )
    viewViva = saveViva.objects.filter(
        assessmentTitle = assesObj
    )
    if request.method == "POST":
        optionA = request.POST.get("opA")
        optionB = request.POST.get("opB")
        optionC = request.POST.get("opC")
        optionD = request.POST.get("opD")
        correctAns = request.POST.get("correct")

        if(correctAns==optionA or correctAns==optionB or correctAns==optionC or correctAns==optionD):
            if request.method == "POST":
                mcq = ExamMCQ.objects.create(
                    q_type = "mcq",
                    question = request.POST.get("mcqqs"),
                    optionA = request.POST.get("opA"),
                    optionB = request.POST.get("opB"),
                    optionC = request.POST.get("opC"),
                    optionD = request.POST.get("opD"),
                    #cohortName = ExamCohort.objects.get(id=cohortObj.id),
                    assessmentTitle=Assestment.objects.get(id=assesObj.id),
                    correctAns = request.POST.get("correct"),
                    marks = request.POST.get("marks"),
                )
        else:
            html = "<html><body><h1>Invalid Correct Answer</h1></body></html>"
            return HttpResponse(html)

    if request.method == "POST":
        viva = saveViva.objects.create(
            qv_type = "viva",
            #type = request.POST.get("vivatype"),
            assessmentTitle=Assestment.objects.get(id=assesObj.id),
            vivaQuestion = request.POST.get("vivaques"),
            vivaMark = request.POST.get("vivamarks"),
            vivaCorrect = request.POST.get("vivacorrect"),
            
        )

    show_viva = list(saveViva.objects.filter(assessmentTitle = assesObj).values_list("vivaQuestion", flat=True))
    vivamarks = list(saveViva.objects.filter(assessmentTitle = assesObj).values_list("vivaMark", flat=True)) 
    vivaans = list(saveViva.objects.filter(assessmentTitle = assesObj).values_list("vivaCorrect", flat=True))
    v_list = json.dumps(show_viva)
    v_mark = json.dumps(vivamarks)
    
    v_ans = json.dumps(vivaans)

    args = {
        "assesObj": assesObj,
        "viewMcq": viewMcq,
        "viewViva": viewViva,
    }
    
    return render(request, "evaluator/inside_assessment_evaluator.html", args)
    
def get_mcq(request, assSlug):
    assObj = get_object_or_404(Assestment, slug=assSlug)
    show_mcq = list(ExamMCQ.objects.filter(assessmentTitle = assObj).values_list("question", flat=True))
    options = list(ExamMCQ.objects.filter(assessmentTitle = assObj).values_list("optionA", "optionB", "optionC", "optionD"))
    cor_ans = list(ExamMCQ.objects.filter(assessmentTitle = assObj).values_list("correctAns", flat=True))
    marks = list(ExamMCQ.objects.filter(assessmentTitle = assObj).values_list("marks", flat=True))
    q_type = list(ExamMCQ.objects.filter(assessmentTitle = assObj).values_list("q_type", flat=True))
    q_list = json.dumps(show_mcq)
    op_list = json.dumps(options)
    correct = json.dumps(cor_ans)
    score = json.dumps(marks)
    q_type = json.dumps(q_type)
    show_viva = list(saveViva.objects.filter(assessmentTitle = assObj).values_list("vivaQuestion", flat=True))
    qv_type = list(saveViva.objects.filter(assessmentTitle = assObj).values_list("qv_type", flat=True)) 
    vivamarks = list(saveViva.objects.filter(assessmentTitle = assObj).values_list("vivaMark", flat=True)) 
    vivaans = list(saveViva.objects.filter(assessmentTitle = assObj).values_list("vivaCorrect", flat=True))
    v_list = json.dumps(show_viva)
    v_mark = json.dumps(vivamarks)
    qv_type = json.dumps(qv_type)
    v_ans = json.dumps(vivaans)
    #context_obj = json.dumps({"questions": show_mcq})
    if request.method == "POST":
        getData = saveExamRecord.objects.create(
            quesType= request.POST.get("type"),
            selectedAns = request.POST.get("selectedOpt"),
            marks = request.POST.get("result_data"),
            total_marks = request.POST.get("t_marks"),
            question = request.POST.get("ques"),
            optionA = request.POST.get("opA"),
            optionB = request.POST.get("opB"),
            optionC = request.POST.get("opC"),
            optionD = request.POST.get("opD"),
            assessmentTitle=Assestment.objects.get(id = assObj.id),
            user = request.user,
        )
        print("data retrieved")
        print(user)
    checkrcd = saveExamRecord.objects.filter(
        assessmentTitle = assObj
    )
    userlist = []
    for i in checkrcd:
        userlist.append(i.user)

    print(userlist)
    flag = 1
    user = request.user
    for j in userlist:
        if(user == j):
            flag = 0
            break
        else:
            flag = 1

    if(flag == 0):
        return redirect("already_taken_exam")
    args = {
         "show_mcq": q_list,
         "options": op_list,
         "cor_ans": correct,
         "marks": score,
         "assObj": assObj,
         "q_type": q_type,
         "show_viva": v_list,
         "vivamarks": v_mark,
         "qv_type": qv_type,
         "vivaans": v_ans,
         "checkrcd": checkrcd,
         "userlist": userlist
    }
    return render(request, "student/take_exam.html", args)

def view_submission(request, sub_slug):
    subCohort_slug = get_object_or_404(ExamCohort, slug=sub_slug)
    email = Student.objects.filter(
        cohortName=subCohort_slug
    )
    args = {
        "email": email,
        "subCohort_slug": subCohort_slug,
    }
    return render(request, "evaluator/view_submissions.html", args) 
    
def view_assessment_submissions(request, assessCo_slug):
    assessCo_slug = get_object_or_404(ExamCohort, slug=assessCo_slug)
    
    avl_assessment = models.Assestment.objects.filter(
        cohort = assessCo_slug
    )
    args = {
        "avl_assessment": avl_assessment,
        "assessCo_slug": assessCo_slug,
    }
  
    return render(request, "evaluator/view_assessment_submissions.html", args) 
    
def view_inside_submissions(request, assuSlug):
    assuObj = get_object_or_404(Assestment, slug=assuSlug)
    user = request.user
    print(user)
    assess_info = models.saveExamRecord.objects.filter(
        assessmentTitle = assuObj,
    ).order_by('-user')
    # viva = models.saveExamRecord.objects.filter(
    #     assessmentTitle = assuObj,
    #     quesType = 'viva',
    # )
    #result_list = list(chain(assess_info, viva))
    #assess_info = saveExamRecord.objects.order_by('-user')
    args = {
        "assess_info": assess_info,
        "assuObj":assuObj,
        
    }
    return render(request, "evaluator/view_inside_submissions.html", args) 
def edit_record(request, pk):
    getmark = saveExamRecord.objects.get(pk=pk)
    print(getmark.marks)
    if request.method == "POST":
        temp = request.POST.get("em")
        print(temp)
        
        getmark.marks=temp
        getmark.save()
    
    args={
        "getmark":getmark
    }  
    return HttpResponseRedirect(request.META.get('HTTP_REFERER'))

def examRecordcsv(request):
  
    response = HttpResponse(content_type='text/csv')
    response['Content-Disposition'] = 'attachment; filename=record.csv'
    
    writer = csv.writer(response)
    
    data = saveExamRecord.objects.filter()
        
    
    writer.writerow(['Username', 'Email', 'Question', 'Type', 'Answer', 'Marks'])
    
    for i in data:
        writer.writerow([i.user, i.question, i.quesType, i.selectedAns, i.marks])

    
    return response

# Login(User), Cohort(ExamCohort), Assessment(Assestment)

class userApiView(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializers

class ExamCohortApiView(viewsets.ModelViewSet):
    queryset = ExamCohort.objects.all()
    serializer_class = ExamCohortSerializers

class StudentApiView(viewsets.ModelViewSet):
    queryset = Student.objects.all()
    serializer_class = StudentSerializers

class AssessmentApiView(viewsets.ModelViewSet):
    queryset = Assestment.objects.all()
    serializer_class = AssessmentSerializers
    
class McqQuesApiView(viewsets.ModelViewSet):
    queryset = ExamMCQ.objects.all()
    serializer_class = MCQSerializers
    
class VivaQuesApiView(viewsets.ModelViewSet):
    queryset = saveViva.objects.all()
    serializer_class = VivaSerializers
    
class saveExamApiView(viewsets.ModelViewSet):
    queryset = saveExamRecord.objects.all()
    serializer_class = saveExamSerializers