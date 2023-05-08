from django.db import models
from django.db import models
import datetime
from django.core.exceptions import ValidationError
from django.contrib.auth.models import User
from datetime import date
from django.conf import settings

import os.path

class ExamCohort(models.Model):
    slug = models.SlugField(unique=True, null=True, blank=True)
    user = models.ForeignKey(User, on_delete=models.CASCADE, null=True)
    cohortName = models.CharField(max_length=50)
    
    def __str__(self):
        return self.cohortName
    
    def save(self, *args, **kwargs):
        self.slug = self.cohortName
        super(ExamCohort, self).save(*args, **kwargs)


class Assestment(models.Model):
    slug = models.SlugField(null=True, blank=True)
    created_at = models.DateField(default=date.today)
    assesment_title = models.CharField(max_length=255, null=True)
    cohort = models.ForeignKey(ExamCohort, on_delete=models.SET_NULL, null=True, blank=True)
    available_time = models.TimeField(blank=True,null=True)
    available_date = models.DateField(blank=True,null=True)
    #available_date = models.DateField(default=datetime.date.today())

    def save(self, *args, **kwargs):
        self.slug = self.assesment_title
        super(Assestment, self).save(*args, **kwargs)
    
    def __str__(self):
        return self.assesment_title
    # class Meta:
    #     ordering = ["-created_at"]
    # def save(self, *args, **kwargs):
    #     if self.available_date < datetime.date.today():
    #         raise ValidationError("The date cannot be in the past!")
    #     super(Assestment, self).save(*args, **kwargs)   
  
    
class Student(models.Model):
    created_at = models.DateField(default=date.today)
    email = models.EmailField(default="", unique=False, max_length=255)
    cohortName = models.ForeignKey(ExamCohort, on_delete=models.CASCADE, null = True)
    user = models.ForeignKey(User, on_delete=models.CASCADE, null=True)
    
    class Meta:
        ordering = ["-id"]
        
    def __str__(self):
        return self.email
    
    
class ExamMCQ(models.Model):
    question = models.CharField(max_length=255, null=True)
    optionA =  models.CharField(max_length=255, null=True)
    optionB =  models.CharField(max_length=255, null=True)
    optionC =  models.CharField(max_length=255, null=True)
    optionD =  models.CharField(max_length=255, null=True)
    correctAns = models.CharField(max_length=255, null=True)
    #cohortName = models.ForeignKey(ExamCohort,on_delete=models.CASCADE, null = True, blank=True)
    user = models.ForeignKey(User, on_delete=models.CASCADE, null=True)
    assessmentTitle = models.ForeignKey(Assestment, on_delete=models.CASCADE, null=True)
    marks = models.FloatField(null=True, blank=True)
    q_type = models.CharField(max_length=50, null=True)
    class Meta:
        ordering = ["-id"]
    
    def __str__(self):
        return self.question
    

class saveExamRecord(models.Model):
    cohortName = models.ForeignKey(ExamCohort,on_delete=models.CASCADE, null = True, blank=True)
    assessmentTitle = models.ForeignKey(Assestment, on_delete=models.CASCADE, null=True)
    question = models.CharField(max_length=255, null=True)
    optionA =  models.CharField(max_length=255, null=True)
    optionB =  models.CharField(max_length=255, null=True)
    optionC =  models.CharField(max_length=255, null=True)
    optionD =  models.CharField(max_length=255, null=True)
    quesType = models.CharField(max_length=50, null=True)
    selectedAns = models.CharField(max_length=255, null=True)
    user = models.ForeignKey(User, on_delete=models.CASCADE, null=True)
    marks = models.FloatField(null=True, blank=True)
    total_marks = models.FloatField(null=True, blank=True)
    class Meta:
        ordering = ["-user"]
        
    def __str__(self):
        return self.question
    
    
class saveViva(models.Model):
    cohortName = models.ForeignKey(ExamCohort,on_delete=models.CASCADE, null = True, blank=True)
    assessmentTitle = models.ForeignKey(Assestment, on_delete=models.CASCADE, null=True)
    vivaQuestion = models.CharField(max_length=255, null=True)
    vivaMark = models.FloatField(null=True, blank=True)
    vivaCorrect = models.CharField(max_length=255, null=True)
    qv_type = models.CharField(max_length=50, null=True)
    #audiofile = models.FileField(null=True)
    class Meta:
        ordering = ["-id"]