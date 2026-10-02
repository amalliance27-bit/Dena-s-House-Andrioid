package com.example.builtbyd.model

object AppContentRepository {

    val principles = listOf(
        Principle("01", "Train for strength", "Focused resistance work replaces hours of aimless cardio."),
        Principle("02", "Build your body", "Training becomes an act of respect, never punishment."),
        Principle("03", "Progress by design", "A planned increase in load, control, or range keeps results moving."),
        Principle("04", "Eat to support change", "Sustainable nutrition fuels muscle, energy, and recovery."),
        Principle("05", "Stay accountable", "Clear targets and honest check-ins turn effort into consistency.")
    )

    val services = listOf(
        CoachingService(
            id = "personal-training",
            title = "1:1 Personal Training",
            tag = "Hamilton · In person",
            description = "Individual coaching, precise form, progressive strength, and sessions shaped around your ability and goals.",
            imageAsset = "assets/media-03-0e963587d2.jpg",
            isPrimary = true
        ),
        CoachingService(
            id = "online-coaching",
            title = "Online Coaching",
            tag = "Anywhere · Online",
            description = "Train with Deana wherever you are, with a structured plan, regular check-ins, and coaching that keeps you moving.",
            imageAsset = null
        ),
        CoachingService(
            id = "programs",
            title = "Built by D Programs",
            tag = "Train independently",
            description = "Structured strength programs you can follow at your pace, with every phase already mapped.",
            imageAsset = null
        ),
        CoachingService(
            id = "womens-strength",
            title = "Women's Strength",
            tag = "Muscle · Confidence · Longevity",
            description = "Build the muscle, stability, and confidence that carry into every decade.",
            imageAsset = "assets/media-04-84548b9678.jpg"
        )
    )

    val testimonials = listOf(
        Testimonial(
            quote = "Love D’s discipline, determination and drive. She is a mentor, trainer and inspirational to everyone she meets.",
            author = "Dean",
            location = "Vancouver, BC"
        ),
        Testimonial(
            quote = "D taught me how to lift properly for the first time. Six months in I am stronger than I was in my twenties, and my back pain is gone.",
            author = "Devon",
            location = "Toronto, ON",
            isSample = true
        ),
        Testimonial(
            quote = "I came in nervous and left every session feeling capable. She explains the why behind every exercise, so nothing feels random.",
            author = "Raeven",
            location = "Windsor, ON",
            isSample = true
        ),
        Testimonial(
            quote = "Online coaching kept me honest. Clear plan, weekly check-ins, and real adjustments when life got busy.",
            author = "Fred",
            location = "Ottawa, ON",
            isSample = true
        ),
        Testimonial(
            quote = "At 54 I wanted mobility and confidence, not a bootcamp. D built exactly that, and I move better than I have in years.",
            author = "Susan",
            location = "Toronto, ON",
            isSample = true
        ),
        Testimonial(
            quote = "She holds a standard without ever making you feel small. Best coaching I have had on either side of the border.",
            author = "Greg",
            location = "Detroit, MI",
            isSample = true
        )
    )

    val yogaPillars = listOf(
        YogaPillar(
            step = "01 / MOVE",
            label = "Move",
            title = "Make room for movement.",
            description = "A softer rhythm beside the water. Kneeling flows, joint freedom, and peaceful resetting.",
            imageAsset = "assets/media-12-1df131e51d.jpg"
        ),
        YogaPillar(
            step = "02 / STRENGTH",
            label = "Strength",
            title = "Control in every movement.",
            description = "Gentle resistance bands, shoulder opening, and postural alignment under the sun.",
            imageAsset = "assets/media-13-2a1751e1a6.jpg"
        ),
        YogaPillar(
            step = "03 / BREATHE",
            label = "Breathe",
            title = "A little room to be yourself.",
            description = "Deep coastal breaths, decompression from weekly stress, and finding joy in movement.",
            imageAsset = "assets/media-14-99e9c5ee1b.jpg"
        ),
        YogaPillar(
            step = "04 / CONNECT",
            label = "Connect",
            title = "Better when it is shared.",
            description = "Strength and friendship on the shore. Movement that brings women together in community.",
            imageAsset = "assets/media-07-35076320a4.jpg"
        )
    )

    val virtualZones = listOf(
        VirtualHouseZone(
            zoneNumber = "Zone 01 · Lifting Floor",
            title = "The Virtual Power Loft",
            description = "Interactive barbell stations, biometric barbell path tracking, and personalized lifting form holograms for heavy compound movements."
        ),
        VirtualHouseZone(
            zoneNumber = "Zone 02 · Recovery Sanctuary",
            title = "Mobility & Breathwork Deck",
            description = "Spatial audio relaxation, 360° ocean movement simulation, and assisted stretching environments designed to down-regulate stress."
        ),
        VirtualHouseZone(
            zoneNumber = "Zone 03 · Private Coaching",
            title = "1-on-1 Avatar Coaching Arena",
            description = "Personal consultations and private technique analysis with Deana inside the 3D space, no matter where in the world you train."
        ),
        VirtualHouseZone(
            zoneNumber = "Zone 04 · Global Community",
            title = "Worldwide Member Pavilion",
            description = "Live group training sessions, synchronized workouts, and community fitness meetups connecting women across North America."
        )
    )

    val socialChannels = listOf(
        SocialChannel(
            name = "Instagram",
            handle = "@builtbyd · Precision Physique",
            description = "Daily workout clips, strength tutorials, and training stories from Hamilton and beyond.",
            url = "https://instagram.com",
            iconKey = "instagram"
        ),
        SocialChannel(
            name = "Facebook Community",
            handle = "Built by D · Women's Strength",
            description = "Articles, hormone optimization tips, community conversations, and Q&As.",
            url = "https://facebook.com",
            iconKey = "facebook"
        ),
        SocialChannel(
            name = "TikTok",
            handle = "@builtbyd_strength",
            description = "Quick lifting cues, mobility routines, and everyday nutrition targets in 60 seconds.",
            url = "https://tiktok.com",
            iconKey = "tiktok"
        ),
        SocialChannel(
            name = "YouTube",
            handle = "Precision Physique Video Hub",
            description = "Full-length exercise demonstrations, warm-up sequences, and deep-dive discussions.",
            url = "https://youtube.com",
            iconKey = "youtube"
        )
    )

    val socialFeed = listOf(
        SocialFeedItem(
            tag = "#BuiltByDCommunity",
            quote = "Movement is always better when it is shared. Strong women cheering on strong women.",
            imageAsset = "friends.png"
        ),
        SocialFeedItem(
            tag = "#StrengthAtEveryAge",
            quote = "Consistency over intensity. Your body reflects the respect you pay it week after week.",
            imageAsset = "Body by Dee.jpg"
        ),
        SocialFeedItem(
            tag = "#PrecisionPhysique",
            quote = "Form first, load second. Precision in the gym translates to power in your daily life.",
            imageAsset = "D gym pose.jpg"
        ),
        SocialFeedItem(
            tag = "#MobilityAndBreathe",
            quote = "Balance the heavy work with mobility, breath, and time under open skies.",
            imageAsset = "yoga beach 4.jpg"
        )
    )

    val articles = listOf(
        // Training
        JournalArticle(
            id = "days-lifting",
            category = ArticleCategory.TRAINING,
            number = "01",
            readTime = "4 min",
            title = "How many days should women lift weights?",
            takeaway = "For most women, two to four weekly strength sessions work exceptionally well.",
            answers = listOf(
                "Two full-body sessions can build strength when time is tight. Three sessions create more room for practice and balanced weekly volume. Four sessions suit women who recover well and enjoy a split routine. The best number is the one that lets you train hard, recover, and return with energy.",
                "Start with three days separated by recovery days. Cover squatting, hinging, pushing, pulling, carrying, and core control across the week. Add a day only when sleep, appetite, joints, and performance remain steady."
            )
        ),
        JournalArticle(
            id = "glutes-after-50",
            category = ArticleCategory.TRAINING,
            number = "02",
            readTime = "6 min",
            title = "How to build glutes after 50",
            takeaway = "Age changes recovery speed. It never erases your ability to build muscle.",
            answers = listOf(
                "Train the glutes through their full job: hip extension, abduction, and pelvic stability. Build around hip thrusts or bridges, squats, Romanian deadlifts, step-ups, and cable or band abduction. Use loads that make the final repetitions demanding while your form stays controlled.",
                "Give the glutes two or three exposures each week, then support the work with enough protein, sleep, and calories. Measure progress through stronger lifts, better range, and repeated photos taken under similar conditions."
            ),
            imageAsset = "assets/media-03-0e963587d2.jpg"
        ),
        JournalArticle(
            id = "getting-stronger",
            category = ArticleCategory.TRAINING,
            number = "03",
            readTime = "5 min",
            title = "Why you’re not getting stronger",
            takeaway = "Random sessions create random results. Strength needs a visible progression.",
            answers = listOf(
                "The usual stall begins with one of four gaps: the working weight stays comfortable, the exercises change too often, recovery falls short, or the lifts are never recorded. Your muscles require a reason to produce more force than they produced before.",
                "Keep the main movements stable for six to eight weeks. Track weight, repetitions, range, and technique. When you reach the top of a rep range with clean form, add a small amount of load and build again."
            )
        ),
        JournalArticle(
            id = "progressive-overload",
            category = ArticleCategory.TRAINING,
            number = "04",
            readTime = "5 min",
            title = "How to progressively overload",
            takeaway = "Progressive overload means gradually asking the body to do more quality work.",
            answers = listOf(
                "Adding weight is one method. You can also complete another repetition, improve control, use a fuller range, reduce assistance, add a set, or perform the same work with cleaner technique. Each method raises the training demand.",
                "Use a simple double progression: choose a range such as eight to twelve repetitions. Keep the same load until every set reaches twelve with steady form. Increase the weight slightly, return to eight, and repeat the climb."
            )
        ),

        // Nutrition
        JournalArticle(
            id = "protein",
            category = ArticleCategory.NUTRITION,
            number = "01",
            readTime = "5 min",
            title = "How much protein do I need?",
            takeaway = "A strong general target is 1.6 to 2.2 grams per kilogram of body weight each day.",
            answers = listOf(
                "A woman weighing 68 kilograms, or roughly 150 pounds, may aim for 110 to 150 grams daily. Your training load, body size, appetite, and calorie target shape the final number. Women new to protein tracking can start near the lower end and build steadily.",
                "Spread protein across three or four meals. Give each meal a clear anchor such as eggs, Greek yogurt, fish, poultry, lean meat, tofu, tempeh, lentils, or a protein shake."
            )
        ),
        JournalArticle(
            id = "eating-less",
            category = ArticleCategory.NUTRITION,
            number = "02",
            readTime = "6 min",
            title = "Why eating less isn’t always better",
            takeaway = "A deeper calorie cut can reduce training quality, movement, recovery, and muscle retention.",
            answers = listOf(
                "Fat loss requires an energy deficit, yet the size of that deficit matters. When food drops too far, strength can fall, hunger can surge, sleep can suffer, and spontaneous movement often declines. The plan becomes harder to continue while the body you want becomes harder to build.",
                "Use a moderate deficit, keep protein high, and let lifting tell the body to retain muscle. Track several weeks of body weight, measurements, energy, and gym performance before changing the plan."
            )
        ),
        JournalArticle(
            id = "recomposition",
            category = ArticleCategory.NUTRITION,
            number = "03",
            readTime = "6 min",
            title = "Building muscle while losing fat",
            takeaway = "Body recomposition works best when strength training, protein, recovery, and patience line up.",
            answers = listOf(
                "Beginners, returning lifters, and women carrying more body fat often have the widest opening for muscle gain during fat loss. Lift three or four days each week, train close enough to muscular fatigue, and progress the major patterns over time.",
                "Keep the calorie deficit modest and center meals on protein and high-fibre foods. Judge change through waist measurements, photos, clothing fit, and strength. The scale can move slowly while the physique changes clearly."
            )
        ),

        // Women
        JournalArticle(
            id = "strength-as-we-age",
            category = ArticleCategory.WOMEN,
            number = "01",
            readTime = "6 min",
            title = "Strength training as we age",
            takeaway = "Training becomes more purposeful as the years pass.",
            answers = listOf(
                "Muscle and power tend to decline when they go untrained. Strength work pushes in the other direction. It helps preserve the force required to climb stairs, carry groceries, rise from the floor, travel, and keep doing the activities that make life yours.",
                "Begin with repeatable movements and a level you can recover from. Technique earns the right to load. Consistency earns the right to progress."
            )
        ),
        JournalArticle(
            id = "muscle-after-40",
            category = ArticleCategory.WOMEN,
            number = "02",
            readTime = "7 min",
            title = "Muscle after 40",
            takeaway = "Your forties can become the decade when training finally gains structure.",
            answers = listOf(
                "Hormonal shifts can affect recovery, sleep, body composition, and training tolerance. The answer is thoughtful programming: enough load to stimulate muscle, enough food to repair it, and enough recovery to repeat the work.",
                "Keep compound patterns in the program and add targeted work for shoulders, back, glutes, hamstrings, and core. Progress may move in smaller steps, yet small steps repeated for a year create a different body."
            )
        ),
        JournalArticle(
            id = "women-resistance-training",
            category = ArticleCategory.WOMEN,
            number = "03",
            readTime = "5 min",
            title = "Why women need resistance training",
            takeaway = "Resistance training protects capacity while shaping the physique.",
            answers = listOf(
                "Muscle supports glucose control, joint stability, posture, energy use, and the ability to handle physical stress. It also gives the body the shape many women seek through dieting alone.",
                "The work can begin with machines, dumbbells, cables, bands, or body weight. The equipment matters less than the pattern: apply resistance, recover, then return slightly more capable."
            )
        ),
        JournalArticle(
            id = "bone-health",
            category = ArticleCategory.WOMEN,
            number = "04",
            readTime = "5 min",
            title = "Bone health and strength",
            takeaway = "Bones respond to force. Progressive resistance and impact give them a reason to remain strong.",
            answers = listOf(
                "Weight-bearing exercise, loaded carries, squats, hinges, presses, pulls, and appropriate impact activities can support bone density. The plan should match your current health, training history, and fracture risk.",
                "Women with osteoporosis, recent fractures, persistent pain, or major medical concerns should coordinate training with a qualified clinician. Coaching can then turn that clinical guidance into safe, consistent practice."
            )
        ),

        // Mindset
        JournalArticle(
            id = "starting-over",
            category = ArticleCategory.MINDSET,
            number = "01",
            readTime = "4 min",
            title = "Starting over",
            takeaway = "You return with experience, even when your current strength feels unfamiliar.",
            answers = listOf(
                "Begin below your maximum. Leave each early session feeling capable of another set. That restraint protects joints, reduces soreness, and gives the routine space to become part of your week.",
                "Choose two or three training days and keep the exercises stable. The first victory is attendance. The second is better technique. Load follows both."
            )
        ),
        JournalArticle(
            id = "body-confidence",
            category = ArticleCategory.MINDSET,
            number = "02",
            readTime = "5 min",
            title = "Body confidence",
            takeaway = "Confidence grows from evidence, and training creates evidence every week.",
            answers = listOf(
                "A changing reflection can matter, yet confidence becomes steadier when it also rests on action: lifting a heavier dumbbell, completing a clean push-up, moving without fear, or keeping a commitment to yourself.",
                "Track performance beside photos and measurements. Your body becomes more than an object to judge. It becomes a partner that can learn and produce force."
            )
        ),
        JournalArticle(
            id = "comfortable-being-strong",
            category = ArticleCategory.MINDSET,
            number = "03",
            readTime = "5 min",
            title = "Getting comfortable being strong",
            takeaway = "Strength may feel unfamiliar before it feels like yours.",
            answers = listOf(
                "Some women learned to stay small in presence, appetite, ambition, or muscle. Training challenges that script. The weight asks for your attention, your breath, and your full effort.",
                "Let strength become ordinary. Take up the space your stance requires. Rest between hard sets. Add weight when you earn it. The body learns; the mind follows."
            )
        )
    )

    val goalOptions = listOf(
        "Weight loss" to "Reduce body fat while protecting strength and muscle.",
        "Hormone-related support" to "Build training, recovery, and nutrition habits for this season.",
        "Mobility" to "Move with greater range, control, and everyday comfort.",
        "Muscle building" to "Add shape, strength, and physical capacity.",
        "Confidence and consistency" to "Build a routine you trust and can continue.",
        "Custom goal" to "Tell Deana in your own words."
    )

    val serviceOptions = listOf(
        "1:1 Personal Training",
        "Online Coaching",
        "Built by D Programs",
        "Women's Strength",
        "I need guidance"
    )

    val experienceOptions = listOf(
        "New to strength training",
        "Returning after time away",
        "Training less than 2 years",
        "Training 2+ years"
    )

    val ageRangeOptions = listOf(
        "Under 30",
        "30–39",
        "40–49",
        "50–59",
        "60+"
    )

    val daysOptions = listOf(
        "2 days",
        "3 days",
        "4 days",
        "5+ days"
    )
}
