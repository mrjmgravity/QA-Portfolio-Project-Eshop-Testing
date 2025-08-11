import { Locator, Page } from "@playwright/test";

export class SignUpPage {
    page: Page;
    radioGender: Locator;
    password: Locator;
    firstName: Locator;
    lastName: Locator;

    constructor (page: Page) {
        this.page = page;
        
    }

    async goToLoginPage() {
        await this.page.goto('https://automationexercise.com/login');
    }

    }
