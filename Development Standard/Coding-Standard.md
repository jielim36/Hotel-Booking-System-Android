# Project Coding Standards
---
### *Naming Standards*
**1. Must using Lower Camel Case naming Style**
> Lower camel case: capitalizing the first letter of each word except the first, which remains lowercase.

*Lower camel case example:*
```java
    Integer studentId = null;
    String studentName = null;
    String studentEmail = null;
    boolean isEmpty = true;

    public void accountVerify(){
        //code
    }
```

**2. To name constant variables, use all uppercase letters with underscores to separate words**
*Example:*
```java
    final int PAGE_NUMBER = 8;
    final int MAX_VALUE = 100;
    final String APP_NAME = "Hotel Booking System";
```

<br>

### *Coding Style Standards*
- The code should achieve **reusability** and **minimize redundancy**, striving **not to write duplicate code**.
- **Using comment** to **describe the purpose** of **each method**, and provide comments for the object when necessary to explain its usage.
- Do not using any chinese word.
- **In submitted code**, **avoid** the  **Log prints** and **System.out.println outputs**; limit their usage to personal testing only.

<br>

### *File Structure*
content...

<br>

### *Android Java Class and Resource Files Naming*
**1. Layout File Naming: Combine the page functionality with the layout type, separated by an underscore.**
Example:
```
Login function and Activity layout type: 
    java class naming: LoginActivity.java
    resouce file naming: login_activity.xml

Other:
    Activity layouts should start with "module_activity".
    Fragment layouts should start with "module_fragment".
    Dialog layouts should start with "module_dialog".
    Include layouts should start with "module_include".
    ListView row layouts should start with "module_list_item".
    RecyclerView item layouts should start with "module_recycle_item".
    GridView row layouts should start with "module_grid_item".
```

**2. Drawable and anim resources should be named in lowercase with underscores**
drawable resources following the pattern: `assetType_functionDescription_controlState_qualifier.`
Example:
```
1. login button image when normal state naming:
btn_login_normal.jpg
2. login button image when pressed state naming:
btn_login_pressed.jpg
```
| Asset Type       | Prefix          | Example                 |
|------------------|-----------------|-------------------------|
| Button           | btn_            | btn_send_pressed.jpg    |
| Icon             | ic_             | ic_app.jpg              |
| Menu             | menu_           | menu_submenu.jpg        |
| Notification     | notification_   | notification_bg_dark.jpg|


anim resources following the pattern:
`animationType_direction`
Example:
```
fade_in.xml
fade_out.xml
slide_in_left.xml
slide_out_right.xml
scale_up.xml
```

**3. Color resources must using #AARRGGBB format**
`#FFAA0000 , #7700FF22`

**4. View resources' id must include the shortname of view type**
| Component Name       | Abbreviation | Example         |
|----------------------|--------------|-----------------|
| LinearLayout         | ll           | login_ll        |
| RelativeLayout       | rl           | main_rl         |
| ConstraintLayout     | cl           | profile_cl      |
| ListView            | lv           | comments_lv     |
| ScrollView           | sv           | details_sv      |
| TextView            | tv           | title_tv        |
| Button              | btn          | submit_btn      |
| ImageView           | iv           | avatar_iv       |
| CheckBox            | cb           | remember_cb     |
| RadioButton          | rb           | choice_rb       |
| EditText            | et           | email_et        |

<br>

### *Application styles standards*
1. all element using **dp** size unit, text using **sp** size unit.

| Design Specification                        | Size (dp) |
|--------------------------------------------|-----------|
| Top margin                                  | 24        |
| Standard large spacing                      | 16        |
| Divider line thickness                      | 0.5       |
| Padding for center box (left/right)         | 16        |
| Text padding (top/bottom)                   | 4         |
| Left and right margin                       | 8         |
| Default view height                         | 48        |
| Spacing for section separators               | 8         |
| Font Sizes                                 |           |
|   Large title                              | 34sp     |
|   Title                                    | 20sp     |
|   Subtitle                                 | 16sp     |
|   Body text                                | 14sp     |
|   Secondary information                    | 12sp     |
|   Hint text                                | 7sp      |
|   Button text                              | 14sp     |
| Common Font Sizes                         |           |
|   Small hint                              | 12sp:     |
|   Body/Button text                        | 14sp      |
|   Subtitle                                | 16sp      |
|   ppBar text                              | 20sp      |
|   Large title                             | 24sp          |
|   Extra large text                        | 34sp/45sp/56sp/112sp           |
| Minimum touch target size                 | 48dp x 48dp |
| Grid System                               |           |
|   Minimum grid unit size                  | 8dp       |
|   Top status bar height                   | 24dp      |
|   Minimum AppBar height                   | 56dp      |
|   Bottom navigation bar height            | 48dp      |
|   Floating action button size             | 56dp x 56dp / 40dp x 40dp |
|   User avatar size                        | 64dp x 64dp / 40dp x 40dp |
|   Small icon touch area                   | 48dp x 48dp |
|   Drawer to screen edge distance           | 56dp      |
|   Card spacing                            | 8dp       |
|   Top/bottom spacing for dividers         | 8dp       |
|   General element margin                  | 16dp      |
|   Screen left/right alignment baseline    | 16dp      |
|   Text left alignment baseline            | 72dp      |

2. Must follow the theme of under values reouces package:
Example:
```xml
strings.xml (values resouces):
    <resources>
        <string name="app_name">Hotel Booking</string>
        <string name="welcome_str">Welcome to the Hotel Booking system!</string>
    </resources>

Login.xml (Layout resouce):
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/app_name"//caled at here
        android:textSize="24sp"
        android:layout_marginBottom="20dp"
        />

    <TextView
        android:id="@+id/welcome_line"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/welcome_str" //called at here
        >

```

<br>

### *Android project version*
1. Android Studio Minimum SDK: **API 24("Nougat";Android 7.0)**
