package com.seostudio.vistar.testproject.utils;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class IntentFilterUtils {

    private static final String[] EMAIL_PACKAGES = new String[]{
            "android.gm",                   // Gmail
            "android.email",                // Android mail
            "kman.AquaMail",                // Aqua Mail
            "trtf.blue",                    // Blue Mail
            "cloudmagic.mail",              // CloudMagic
            "syntomo.email",                // Email for Exchange
            "android.apps.inbox",           // Inbox by Gmail
            "fsck.k9",                      // K-9 mail
            "mail.mailapp",                 // Mail.ru
            "my.mail",                      // myMail
            "com.wemail",                   // WeMail
            "mobile.client.android.mail"    // Yahoo mail
    };

    private static final String[] MSG_PACKAGES = new String[]{
            "mms",                          // Android SMS app
            "p1.chompsms",                  // chomp SMS
            "nextsms",                      // Handcent SMS
            "jb.gosms",                     // Go SMS
            "android.talk",                 // Hangouts
            "hellotext.hello",              // Hello SMS
            "ninja.sms.promo",              // Ninja SMS (HoverChat)
            "com.textra",                   // Textra SMS
    };

    private IntentFilterUtils() {
    }

    public static boolean isPackageInstalled(PackageManager pm, Intent messageIntent, String... lookupPackages) {
        return !findMatchingResolveInfo(pm, messageIntent, lookupPackages).isEmpty();
    }

    public static Intent filterSendAction(PackageManager pm, Intent messageIntent, String... lookupPackages) {
        Collection<ResolveInfo> matchingResInfo = findMatchingResolveInfo(pm, messageIntent, lookupPackages);

        if (!matchingResInfo.isEmpty()) {
            if (matchingResInfo.size() == 1) {
                messageIntent.setPackage(matchingResInfo.iterator().next().activityInfo.packageName);
            } else {
                List<LabeledIntent> intentList = new ArrayList<>();
                for (ResolveInfo resolveInfo : matchingResInfo) {
                    Intent intent = new Intent(messageIntent);
                    ActivityInfo activityInfo = resolveInfo.activityInfo;
                    intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
                    intentList.add(new LabeledIntent(intent, activityInfo.packageName, resolveInfo.loadLabel(pm), resolveInfo.icon));
                }
                LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);
                messageIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
            }
        }
        return messageIntent;
    }

    public static Intent filterEmailAction(PackageManager pm, Intent intent) {
        return IntentFilterUtils.filterSendAction(pm, intent, EMAIL_PACKAGES);
    }

    public static Intent filterMessageAction(PackageManager pm, Intent intent) {
        return IntentFilterUtils.filterSendAction(pm, intent, MSG_PACKAGES);
    }

    public static Collection<ResolveInfo> findMatchingEmail(PackageManager pm, Intent messageIntent) {
        return findMatchingResolveInfo(pm, messageIntent, EMAIL_PACKAGES);
    }

    public static Collection<ResolveInfo> findMatchingMessage(PackageManager pm, Intent messageIntent) {
        return findMatchingResolveInfo(pm, messageIntent, MSG_PACKAGES);
    }

    public static Collection<ResolveInfo> findAllMatching(PackageManager pm, Intent messageIntent) {
        return pm.queryIntentActivities(messageIntent, 0);
    }

    static Collection<ResolveInfo> findMatchingResolveInfo(PackageManager pm, Intent messageIntent, String... lookupPackages) {
        Collection<ResolveInfo> resInfo = findAllMatching(pm, messageIntent);
        Collection<ResolveInfo> matchingResInfo = new HashSet<>(resInfo.size());
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            for (String lookupPackage : lookupPackages) {
                if (packageName.contains(lookupPackage)) {
                    matchingResInfo.add(resolveInfo);
                    break;
                }
            }
        }
        return matchingResInfo;
    }
}