const express = require('express');
const passport = require('passport');
const GoogleStrategy = require('passport-google-oauth20').Strategy;
const app = express(); const PORT = 8080;
// Configure Passport.js with your Google API credentials
passport.use(  new GoogleStrategy({
        clientID: '58716057596-tvg4s92csqvs1tn688ip40sqm4ne75qm.apps.googleusercontent.com',
        clientSecret: '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',
        callbackURL: 'http://localhost:8080/auth/google/callback',
    },(accessToken, refreshToken, profile, done) => {

// You can access the user's Google profile information here
        console.log(profile);
        return done(null, profile);
    }
));

app.use(passport.initialize());
// Handle the Google Sign-In callback
app.get('/auth/google/callback',
    passport.authenticate('google', {
        session: false
    }),(req, res) => {
        // Redirect or send a response back to the client
        res.send('Authentication successful');
    });

// Start the server
app.listen(PORT, () => {
    console.log(`Server running at http://localhost:${PORT}`);
});