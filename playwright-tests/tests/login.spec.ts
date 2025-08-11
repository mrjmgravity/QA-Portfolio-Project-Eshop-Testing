import { test, expect } from '@playwright/test';
import { LoginPage } from '../page-objects/LoginPage';
import { dismissConsent } from '../utils/dismissConsent';

test.beforeEach(async ({ page }) => {
  
  await page.goto('https://automationexercise.com/login');
  await dismissConsent(page);
});

test('Successful login', async ({ page }) => {
  const loginPage = new LoginPage(page);
  await loginPage.login();
  await expect(page).toHaveURL('https://automationexercise.com/signup');
});