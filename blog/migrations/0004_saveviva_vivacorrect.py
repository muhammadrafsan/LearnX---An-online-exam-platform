# Generated by Django 4.0.5 on 2022-08-10 16:34

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('blog', '0003_saveviva_audiofile'),
    ]

    operations = [
        migrations.AddField(
            model_name='saveviva',
            name='vivaCorrect',
            field=models.CharField(max_length=255, null=True),
        ),
    ]
