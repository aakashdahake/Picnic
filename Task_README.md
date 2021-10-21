# Picnic Recruitment Task #

Please read the following instructions carefully and make sure that you fulfil
all requirements listed.

## Overview ##

This is a QA Test Automation assignment we've created specifically for our
recruitment process.
You were given a link to GitHub, which when you visited that link,
created a private fork of this repository. Only you and developers at Picnic
can see the code you push to this repository.

High-level instructions:

1. Read and follow the task specified below.
2. Make a local clone of this repository on your machine, and do your work on a
   branch other than `master`. Do not make any changes to the `master` branch.
3. Push your changes as frequently as you like to `origin/your-branch-name`,
   and create a pull request to merge your changes back into the `master`
   branch. Don't merge your pull request. Once you're finished with the
   assignment, we will do a code review of your pull request.
4. When you're finished, [create and add][github-labels] the label `done` to
   your pull request. This will notify us that your code is ready to be
   reviewed. Please do **NOT** publish your solution on a publicly available
   location (such as a public GitHub repository, your personal website, _et
   cetera_).

This process closely mimics our actual development and review cycle. We hope
you enjoy it!

## Task ##

We would like you to implement a test automation solution for GitHub Gists API. Before the implementation, please familiarize yourself with official GitHub documentation as a single source of functional requirements for API behavior. Below you will find a list of technical requirements for the assignment. We expect you to test authorized/unauthorized scenarios where applicable.

## Technical Requirements ##

1. Programming language:
    * Java
    * Python
2. Test approach: you are free to choose between traditional xUnit test code style or BDD (Gherkin-based) testing frameworks
3. Minimum required list of scenarios to be tested:
    * Non-functional attributes:
        * [Gists accessibility][gists-auth]
        * [Rate limiting quotas][rate-limiting]
    * Functional behavior of Gists:
        * [Creating a Gist][gists-create]
        * [Reading a Gist][gists-read]
        * [Deleting a Gist][gists-delete]
        * Listing gists for a user ([authenticated][gists-all] & [unauthenticated][gists-all-public])
4. Tests should verify the functionality of the endpoint, not simply assert against a response code
5. Project instructions should specify:
    * Overview of the language / framework used
    * How to set up dependencies and environment for the project
    * How to execute the tests from command line

### Useful information ###

* Gists API Documentation: [GitHub Gists][github-gists]
* For [authorization][github-oauth2] you can [generate][github-tokens] Personal OAuth token with `gists` scope

### Grading Criteria ###

You will be assessed on the following criteria:

* Project structure and approach
* Code readability and style
* Ease of tests setup and execution
* Self-documentation of the tests
* Logical reasoning behind test scenarios
* Bonus points for providing a setup script or dockerized environment

_Thanks in advance for your time and interest in Picnic!_

[github-labels]: https://help.github.com/articles/about-labels
[github-gists]: https://developer.github.com/v3/gists/
[github-tokens]: https://github.blog/2013-05-16-personal-api-tokens/
[github-oauth2]: https://developer.github.com/v3/#oauth2-token-sent-in-a-header
[gists-auth]: https://developer.github.com/v3/gists/#authentication
[gists-all]: https://developer.github.com/v3/gists/#list-gists-for-the-authenticated-user
[gists-all-public]: https://developer.github.com/v3/gists/#list-gists-for-a-user
[gists-read]: https://developer.github.com/v3/gists/#get-a-gist
[gists-create]: https://developer.github.com/v3/gists/#create-a-gist
[gists-delete]: https://developer.github.com/v3/gists/#delete-a-gist
[rate-limiting]: https://developer.github.com/v3/rate_limit/
