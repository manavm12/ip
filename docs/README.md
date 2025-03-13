# Buddy User Guide

## Introduction
Buddy is your friendly neighborhood chatbot that helps you manage tasks efficiently.
Whether it’s a simple to-do, a deadline, or an event, Buddy ensures you stay on top of your schedule.
This guide will walk you through Buddy’s features and how to use them.

## Features

### Adding To-Do : `todo`

Adds a new todo task to your list

**Format:** `todo TASK_DESCRIPTION`
- Creates a todo with the given description.
- The todo will be stored in the task list.

**Example:** `todo buy groceries`

**Expected Output:**
```
Understood buddy! I've added it to your task list
    [T][ ] Buy groceries
You know what to do with 1 task!
```

<br/>

### Adding Deadlines : `deadline`

Adds a new deadline task to your list with a specific dur date/time

**Format:** `deadline TASK_DESCRIPTION /by DATE/TIME`
- Creates a deadline with the given description and due date.
- It is optional to input the date in the format `dd-MM-yyyy HHmm` or `"yyyy-MM-dd HHmm`
- The output will be formatted as `MMM dd yyyy, hh:mma`

**Example 1:** `deadline Watch lecture /by tonight`

**Expected Output:**
```
Understood buddy! I've added it to your task list
    [D][ ] Watch lecture (by: tonight)
You know what to do with 2 tasks!
```

**Example 2:** `deadline Submit assignment /by 15-03-2025 2359`

**Expected Output:**
```
Understood buddy! I've added it to your task list
    [D][ ] Submit assignment (by: Mar 15 2025, 11:59PM)
You know what to do with 3 tasks!
```
<br/>

### Adding Events : `event`

Adds a new event task to your list with a start time and end time

**Format:** `event TASK_DESCRIPTION /from START_TIME /to END_TIME`
- Creates an event with the given description, start time and end time.
- It is optional to input the date in the format `dd-MM-yyyy HHmm` or `"yyyy-MM-dd HHmm`
- The output will be formatted as `MMM dd yyyy, hh:mma`

**Example 1:** `event Team meeting /from Friday 10 am /to Friday 12 pm`

**Expected Output:**
```
Understood buddy! I've added it to your task list
    [E][ ] Team meeting (from: Friday 10 am to: Friday 12 pm)
You know what to do with 4 tasks!
```

**Example 2:** `event Attend party /from 14-03-2025 2200 /to 15-03-2025 0000`

**Expected Output:**
```
Understood buddy! I've added it to your task list
    [E][ ] Attend part (from: Mar 14 2025, 10:00PM to: Mar 15 2025, 12:00AM)
You know what to do with 5 tasks!
```
<br/>

### Listing tasks : `list`

Displays all tasks in the task list.

**Format:** `list`
- Shows all tasks with their current status, in the order they were added

**Example:** `list`

**Expected Output:**
```
--------------------------
1. [T][ ] Buy groceries
2. [D][ ] Watch lecture (by: tonight)
3. [D][ ] Submit assignment (by: Mar 15 2025, 11:59PM)
4. [E][ ] Team meeting (from: Friday 10 am to: Friday 12 pm)
5. [E][ ] Attend Party (from: Mar 14 2025, 10:00PM to: Mar 15 2025, 12:00AM)
--------------------------
```

<br/>

### Marking Tasks as Done : `mark`

Marks a task as completed.

**Format:** `mark TASK_INDEX`
- Marks the task at the specified index as done

**Example:** `mark 3`

**Expected Output:**
```
--------------------------
There you go! Good job finishing that task
[D][X] Submit assignment (by: Mar 15 2025, 11:59PM)
--------------------------
```

<br/>

### Unmarking Tasks : `unmark`

Marks a task as incomplete.

**Format:** `unmark TASK_INDEX`
- Unmarks the task at the specified index, indicating it is not complete

**Example:** `unmark 3`

**Expected Output:**
```
--------------------------
I've marked this task as not done. Go for it and complete it!
[D][] Submit assignment (by: Mar 15 2025, 11:59PM)
--------------------------
```

<br/>

### Deleting Tasks : `delete`

Removes a task from the list.

**Format:** `delete TASK_INDEX`
- Deletes the task at the specified index

**Example:** `delete 5`

**Expected Output:**
```
--------------------------
Done. I have deleted this task
    [E][ ] Attend Party (from: Mar 14 2025, 10:00PM to: Mar 15 2025, 12:00AM)
Now you have 4 tasks remaining
--------------------------
```

<br/>

### Finding Tasks : `find`

Finds tasks that contain a specific keyword.

**Format:** `find KEYWORD`
- Finds all the task that contain the specific keyword

**Example:** `find meeting`

**Expected Output:**
```
--------------------------
Here are the matching tasks in your list:
1. [E][ ] Team meeting (from: Mar 14 2025, 10:00AM to: Mar 14 2025, 12:00PM)
2. [D][ ] Finish meeting slides (by: Thursday, 6pm)
--------------------------
```

<br/>

### Exiting Buddy : `bye`

Exits Buddy chatbot

**Format:** `bye`
- Closes the chatbot with a goodbye message

**Example:** `bye`

**Expected Output:**
```
--------------------------
Alright then! See you later. You know where to find me.
--------------------------
```


