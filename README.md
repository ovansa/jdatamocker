# JDataMocker

**Generate realistic mock data with zero dependencies**

### 📦 Installation

#### GitHub Packages (Recommended)

1. Add this to your `pom.xml`:

```xml

<repository>
    <id>github</id>
    <name>GitHub Packages</name>
    <url>https://maven.pkg.github.com/ovansa/jdatamocker</url>
</repository>

<dependency>
<groupId>com.ovansa</groupId>
<artifactId>jdatamocker</artifactId>
<version>1.0.5</version>
</dependency>
```

2. Create
   a [GitHub PAT](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry#authenticating-to-github-packages)
   and add it to your `~/.m2/settings.xml`:

```xml

<server>
    <id>github</id>
    <username>YOUR_GITHUB_USERNAME</username>
    <password>YOUR_PAT</password>
</server>
```

#### JitPack (Alternative)

```xml

<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
<dependency>
<groupId>com.github.ovansa</groupId>
<artifactId>jdatamocker</artifactId>
<version>v1.0.5</version>
</dependency>
```

---

### 🚀 Usage

```java
JDataMocker mocker = new JDataMocker();

// Generate culturally specific names
String arabicName = mocker.name().arabic();    // "Fatima Al-Maktoum"
String westernName = mocker.name().western();  // "John Smith"

// Other data types
LocalDate nextWeek = mocker.date().future(1);  // Date within next year
String email = mocker.email().business();      // "contact@startup.com"
```

---

### 🌟 Key Features

- **Cultural name support**: `.arabic()`, `.nigerian()`, `.western()`
- **Thread-safe**: Built for concurrent usage
- **No config**: Just instantiate and generate

---

### 📚 Examples

| Category    | Code                         | Sample Output         |
|-------------|------------------------------|-----------------------|
| **Names**   | `mocker.name().nigerian()`   | "Adebayo Okoro"       |
| **Dates**   | `mocker.date().birthday(25)` | `1998-03-14` (age 25) |
| **Numbers** | `mocker.number().even(1,50)` | `24` (even)           |

---

### 🔧 Why Simple Wins

- **No locales**: Explicit methods > hidden magic
- **No builders**: Avoid unnecessary complexity
- **GitHub Packages**: Secure, versioned releases

---

### 📜 License

MIT © [Muhammed Ibrahim](https://github.com/ovansa)