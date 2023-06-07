# FormsGitJenkins

General Agenda: 

A. Run tests on Jenkins with GitHub as the workspace source.
1. Why- Write new tests and merge them to the main repo. Track version changes. Access projects via Jenkins remotely
2. How- Connect git to local machine. Sync with GitHub. Sync with Jenkins. Sync with VScode. Fix pom.xml file. Add external Jars
3. End goal- CI/CD pipelines for the automated tests to run on each version release.
4. News- Success! Managed to run a Jenkins job with GitHub as a source, and HTML reports of ExtentReports! 

B. Run Jenkins independently as a service on qa19 with an ip address
1. Why- Have it continuesly available without the need of a person to run it in qa19 server. Run builds automatically with a Webhook on GitHub
