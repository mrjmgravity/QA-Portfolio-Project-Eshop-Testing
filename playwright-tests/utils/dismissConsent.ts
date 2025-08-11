import { expect, Page } from '@playwright/test';   

export async function dismissConsent(page: Page) {
  const consentRoot = page.locator('.fc-consent-root');

  if (await consentRoot.isVisible()) {
    const acceptBtn = consentRoot.locator('.fc-button:has(.fc-button-label:has-text("Consent"))');

    if (await acceptBtn.isVisible()) {
      await acceptBtn.click();
      await expect(consentRoot).toBeHidden({ timeout: 5000 });
      console.log('✅ Cookie banner dismissed');
    }
  }
}