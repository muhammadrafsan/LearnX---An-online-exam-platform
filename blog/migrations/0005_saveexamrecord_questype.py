# Generated by Django 4.0.5 on 2022-08-14 20:38

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('blog', '0004_saveviva_vivacorrect'),
    ]

    operations = [
        migrations.AddField(
            model_name='saveexamrecord',
            name='quesType',
            field=models.CharField(max_length=50, null=True),
        ),
    ]
