package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    @FindBy(id = "logoutId")
    private WebElement logoutButton;

    @FindBy(id = "fileUpload")
    private WebElement fileUploadButton;

    @FindBy(id = "addNewNoteId")
    private WebElement addNewNoteButton;

    @FindBy(id = "addNewCredId")
    private WebElement AddNewCredentialButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleText;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "note-description")
    private WebElement NoteDescText;

    @FindBy(id = "saveChangesId")
    private WebElement saveChangesButton;

    @FindBy(id = "noteTitleId")
    private WebElement tableNoteTitle;

    @FindBy(id = "noteDescId")
    private WebElement tableNoteDescription;

    @FindBy(id = "noteEditId")
    private WebElement editNoteButton;

    @FindBy(id = "credEditId")
    private WebElement editCredButton;

    @FindBy(id = "noteDeleteId")
    private WebElement deleteNoteButton;

    @FindBy(id = "credDeleteId")
    private WebElement deleteCredButton;

    @FindBy(id = "credential-url")
    private WebElement credUrlText;

    @FindBy(id = "credential-username")
    private WebElement credUserNameText;

    @FindBy(id = "credential-password")
    private WebElement credPasswordText;

    @FindBy(id = "credSaveChangesId")
    private WebElement credSaveChangesButton;

    @FindBy(id = "credUrlId")
    private WebElement tableCredUrl;

    @FindBy(id = "credUsernameId")
    private WebElement tableCredUsername;

    @FindBy(id = "credPasswordId")
    private WebElement tableCredPassword;

    private final JavascriptExecutor js;

    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 500);
    }

    public void logout() {
        js.executeScript("arguments[0].click();", logoutButton);
    }

    public void editNote() {
        js.executeScript("arguments[0].click();", editNoteButton);
    }

    public void editCredential() {
        js.executeScript("arguments[0].click();", editCredButton);
    }

    public void deleteNote() {
        js.executeScript("arguments[0].click();", deleteNoteButton);
    }

    public void deleteCredential() {
        js.executeScript("arguments[0].click();", deleteCredButton);
    }

    public void uploadFile() {
        js.executeScript("arguments[0].click();", fileUploadButton);
    }

    public void addNewNote() {
        js.executeScript("arguments[0].click();", addNewNoteButton);
    }

    public void addNewCredential() {
        js.executeScript("arguments[0].click();", AddNewCredentialButton);
    }

    public void setNoteTitle(String noteTitle) {
        js.executeScript("arguments[0].value='" + noteTitle + "';", noteTitleText);
    }

    public void setCredentialUrl(String url) {
        js.executeScript("arguments[0].value='" + url + "';", credUrlText);
    }

    public void setCredentialUsername(String username) {
        js.executeScript("arguments[0].value='" + username + "';", credUserNameText);
    }

    public void setCredentialPassword(String password) {
        js.executeScript("arguments[0].value='" + password + "';", credPasswordText);
    }

    public void modifyNoteTitle(String newNoteTitle) {
        wait.until(ExpectedConditions.elementToBeClickable(noteTitleText)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitleText)).sendKeys(newNoteTitle);
    }

    public void modifyNoteDescription(String newNoteDescription) {
        wait.until(ExpectedConditions.elementToBeClickable(NoteDescText)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(NoteDescText)).sendKeys(newNoteDescription);
    }

    public void navToNotesTab() {
        js.executeScript("arguments[0].click();", navNotesTab);
    }

    public void navToCredentialsTab() {
        js.executeScript("arguments[0].click();", navCredentialsTab);
    }

    public void setNoteDescription(String noteDescription) {
        js.executeScript("arguments[0].value='"+ noteDescription +"';", NoteDescText);
    }

    public void saveNoteChanges() {
        js.executeScript("arguments[0].click();", saveChangesButton);
    }

    public void saveCredentialChanges() {
        js.executeScript("arguments[0].click();", credSaveChangesButton);
    }

    public boolean noNotes(WebDriver driver) {
        return !isElementPresent(By.id("noteTitleId"), driver) && !isElementPresent(By.id("noteTitleId"), driver);
    }

    public boolean noCredentials(WebDriver driver) {
        return !isElementPresent(By.id("credUrlId"), driver) &&
                !isElementPresent(By.id("credUsernameId"), driver) &&
                !isElementPresent(By.id("credPasswordId"), driver);
    }

    public boolean isElementPresent(By locatorKey, WebDriver driver) {
        try {
            driver.findElement(locatorKey);

            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public Note getFirstNote() {
        String title = wait.until(ExpectedConditions.elementToBeClickable(tableNoteTitle)).getText();
        String description = tableNoteDescription.getText();

        return new Note(-1, title, description,-1);
    }

    public Credential getFirstCredential() {
        String url = wait.until(ExpectedConditions.elementToBeClickable(tableCredUrl)).getText();
        String username = tableCredUsername.getText();
        String password = tableCredPassword.getText();

        return new Credential(-1,url, username, "", password,-1);
    }
}