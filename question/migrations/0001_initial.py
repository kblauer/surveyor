# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('survey', '0002_auto_20150409_2247'),
    ]

    operations = [
        migrations.CreateModel(
            name='Question',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('questionName', models.CharField(max_length=50)),
                ('questionDescription', models.TextField()),
                ('priority', models.IntegerField(default=0)),
                ('createTime', models.DateTimeField(auto_now_add=True)),
                ('updateTime', models.DateTimeField(auto_now=True)),
                ('survey', models.ForeignKey(to='survey.Survey')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
    ]
