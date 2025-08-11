import { Locator, Page } from "@playwright/test";

export class LoginPage {
    page: Page;
    userName: Locator;
    userEmail: Locator;
    signUpButton: Locator;

    constructor (page: Page) {
        this.page = page;
        this.userName = page.getByRole('textbox', { name: 'Name' });
        this.userEmail = page.locator('input[data-qa="signup-email"]');
        this.signUpButton = page.locator('[data-qa="signup-button"]');
    }

    async goToLoginPage() {
        await this.page.goto('https://automationexercise.com/login');
    }

    async enterUserName(){
        await this.userName.fill('Darren');
    }

    async enterInvalidUserName(){
        await this.userName.fill('4');
    }

    async enterEmail(){
        await this.userEmail.fill('darren@test.com');
    }

    async enterInvalidEmail(){
        await this.userEmail.fill('4');
    }

    async clickSignUpButton() {
        await this.signUpButton.click();
    }

    async login(){
        await this.userName.fill('Darren');
        await this.userEmail.fill('darren@test.com');
        await this.signUpButton.click();
    }
}