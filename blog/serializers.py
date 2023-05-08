from rest_framework import serializers
from .models import ExamCohort, ExamMCQ, Student, User, Assestment, saveExamRecord, saveViva
from rest_framework.fields import CurrentUserDefault
class StudentSerializers(serializers.ModelSerializer):
    cohortName = serializers.CharField(source='cohortName.cohortName')
    user = serializers.CharField(source='user.username')
    class Meta:
        model = Student
        fields = ["email", "cohortName", "user"]

class ExamCohortSerializers(serializers.ModelSerializer):
    class Meta:
        model = ExamCohort
        fields = [ "cohortName", "user", "slug"]

class AssessmentSerializers(serializers.ModelSerializer):
    class Meta:
         model = Assestment
         fields = ["slug", "assesment_title", "available_date", "available_time", "cohort"]

class UserSerializers(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ["username","email"]

class MCQSerializers(serializers.ModelSerializer):
    class Meta:
        model = ExamMCQ
        fields = ["question", "optionA", "optionB", "optionC", "optionD", "correctAns", "user", "assessmentTitle", "marks", "q_type"]

class VivaSerializers(serializers.ModelSerializer):
    class Meta:
        model = saveViva
        fields = ["cohortName", "assessmentTitle", "vivaQuestion", "vivaMark", "vivaCorrect", "qv_type"]

class saveExamSerializers(serializers.ModelSerializer):
    #assessmentTitle = serializers.CharField(source='assessmentTitle.assesment_title')
    assessmentTitle = serializers.StringRelatedField()
    def create(self, validated_data):
        obj = saveExamRecord(**validated_data)
        obj.owner = CurrentUserDefault()
        obj.save()
        return obj
    class Meta:
        model = saveExamRecord
        fields = ["cohortName", "assessmentTitle", "question", "optionA", "optionB", "optionC", "optionD", "quesType", "selectedAns", "user", "marks", "total_marks"]