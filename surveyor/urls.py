from django.conf.urls import patterns, include, url
from django.contrib import admin

# These patterns generate how the URL structure of the overall site will work.  
# All URL's route through this particular page, always.  
# This particular page acts as a controller for the rest of the URL's, 
# sending each request to the correct app

urlpatterns = patterns('',
                       
    (r'^profile/', include('userprofile.urls')),
    (r'^survey/', include('survey.urls')),
    
    # *** Homepage URL ***
    url(r'^$', 'surveyor.views.home', name='home'),
    url(r'^about/$', 'surveyor.views.about', name='about'),
    url(r'^services/$', 'surveyor.views.services', name='services'),
    url(r'^contact/$', 'surveyor.views.contact', name='contact'),

    url(r'^dash/$', 'surveyor.views.dashboard', name='dashboard'),

    # *** Admin site URL ***
    # This enables the django admin site and it's in-browser features.  
    # It allows full CRUD access to the database
    url(r'^admin/', include(admin.site.urls)),
    
    # *** User Authorization URL patterns ***
    # this includes the browser-side parsing of the login data (/auth),
    # the logout request (/request),
    # the successful login redirect (/success), 
    # and the page reached if invalid login information is sent (/invalid).
    url(r'^user/auth/$', 'surveyor.views.auth_view'),
    url(r'^user/logout/$', 'surveyor.views.logout'),
    url(r'^user/success/$', 'surveyor.views.success'),
    url(r'^user/invalid/$', 'surveyor.views.invalid_login'),
    
    
)

admin.site.site_header = 'Surveyor Site Administration'