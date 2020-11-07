## Design Decisions
1. To save the cost and simplify the design, we will using the Desktop application
1. [Java FX](https://openjfx.io) will be used as UI framework
1. For back-end we will be using the [SQLLite](https://www.sqlite.org/index.html)
1. Primary language will be used for all labels and texts are Kannada. However, we will also allow the user to enter either in English or Kannada language in the field during the usage
1. For persistence for we will be using the [Hibernate](https://hibernate.org/orm/)

## Open issues
* SQLLite does not have default dialect for Hibernate. For now we are can go with H2 dialect but it going cause problem soon. We need to move to either H2 or write custom dialect for SQLLite

## Data Model
![Data Model](./Datamodel.png)