import com.example.patienttracker.domain.model.Profile
import com.example.patienttracker.presentation.profile.ProfileData

// ProfileConverters.kt
fun ProfileData.toProfile(): Profile {
    return Profile(
        name = this.name,
        email = this.email,
        phone = this.phone,
        address = this.address,
        someOtherField = this.someOtherField
    )
}

fun Profile.toProfileData(): ProfileData {
    return ProfileData(
        name = this.name,
        email = this.email,
        phone = this.phone,
        address = this.address,
        someOtherField = this.someOtherField
    )
}
